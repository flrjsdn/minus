package com.hexa.muinus.common.batch.config;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import com.hexa.muinus.common.batch.reader.DailySalesItemReader;
import com.hexa.muinus.store.domain.transaction.DailySales;
import com.hexa.muinus.store.domain.transaction.repository.DailySalesRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing(dataSourceRef = "metaDBSource", transactionManagerRef = "metaTransactionManager")
public class DailySalesBatchConfig {

    private final PlatformTransactionManager metaTransactionManager;
    private final DataSource dataDBSource;
    private final JobRepository jobRepository;

    public DailySalesBatchConfig(
            @Qualifier("jobRepository") JobRepository jobRepository,
            @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
            @Qualifier("dataDBSource") DataSource dataDBSource) {
        this.jobRepository = jobRepository;
        this.metaTransactionManager = metaTransactionManager;
        this.dataDBSource = dataDBSource;
    }

    @Bean
    public Job DailySalesBatchJob() throws Exception {
        try {
            log.info("Daily Sales Batch Job 생성");
            return new JobBuilder("DailySalesBatchJob", jobRepository)
                    .start(dailySalesStep())
                    .build();
        } catch (Exception e) {
            log.error("배치 Job 생성 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.JOB_CREATION_FAILED, e);
        }
    }

    @Bean
    public Step dailySalesStep() throws Exception {
        try {
            log.info("Daily Sales Step 생성");
            return new StepBuilder("dailySalesStep", jobRepository)
                    .<DailySales, DailySales>chunk(10, metaTransactionManager)
                    .allowStartIfComplete(true)
                    .reader(dailySalesReader(dataDBSource))
                    .writer(dailySalesWriter(dataDBSource))
                    .build();
        } catch (Exception e) {
            log.error("배치 Step 생성 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.STEP_CREATION_FAILED, e);
        }
    }

    @Bean
    public DailySalesItemReader dailySalesReader(@Qualifier("dataDBSource") DataSource dataDBSource) {
        try {
            return new DailySalesItemReader(dataDBSource);
        } catch (Exception e) {
            log.error("Reader 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.READER_INITIALIZATION_FAILED, e);
        }
    }

    @Bean
    public JdbcBatchItemWriter<DailySales> dailySalesWriter(@Qualifier("dataDBSource") DataSource dataDBSource) {
        try {
            return new JdbcBatchItemWriterBuilder<DailySales>()
                    .dataSource(dataDBSource)
                    .sql("""
                        INSERT INTO hexa.daily_sales (sale_date, store_no, item_id, total_quantity, total_amount) 
                        VALUES (:saleDate, :storeNo, :itemId, :totalQuantity, :totalAmount) 
                        ON DUPLICATE KEY UPDATE
                        total_quantity = VALUES(total_quantity),
                        total_amount = VALUES(total_amount)
                        """)
                    .beanMapped()
                    .build();
        } catch (Exception e) {
            log.error("Writer 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.WRITER_INITIALIZATION_FAILED, e);
        }
    }
}
