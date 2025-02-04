package com.hexa.muinus.common.batch;

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

    private final PlatformTransactionManager platformTransactionManager;
    private final PlatformTransactionManager dataTransactionManager;
    private final DataSource metaDBSource;
    private final DataSource dataDBSource;
    private final EntityManagerFactory dataEntityManagerFactory;
    private final DailySalesRepository dailySalesRepository;

    public DailySalesBatchConfig(
            @Qualifier("metaTransactionManager") PlatformTransactionManager platformTransactionManager,
            @Qualifier("dataTransactionManager") PlatformTransactionManager dataTransactionManager,
            @Qualifier("metaDBSource") DataSource metaDBSource,
            @Qualifier("dataDBSource") DataSource dataDBSource,
            @Qualifier("dataEntityManager") EntityManagerFactory dataEntityManagerFactory,
            DailySalesRepository dailySalesRepository) {
        this.platformTransactionManager = platformTransactionManager;
        this.dataTransactionManager = dataTransactionManager;
        this.metaDBSource = metaDBSource;
        this.dataDBSource = dataDBSource;
        this.dataEntityManagerFactory = dataEntityManagerFactory;
        this.dailySalesRepository = dailySalesRepository;
    }

    /**
     * JobRepository를 메타 데이터 DB에 저장하도록 설정 (metaDB 사용)
     */
    @Bean
    public JobRepository batchJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(metaDBSource);
        factory.setTransactionManager(platformTransactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        factory.setTablePrefix("BATCH_");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * Job 정의 (batchJobRepository()를 직접 참조)
     */
    @Bean
    public Job DailySalesBatchJob() throws Exception {
        log.info("Daily Sales Batch Job");
        return new JobBuilder("DailySalesBatchJob", batchJobRepository())
                .start(dailySalesStep())
                .build();
    }

    /**
     * Step 정의 (batchJobRepository()를 직접 참조)
     */
    @Bean
    public Step dailySalesStep() throws Exception {
        log.info("Daily Sales Step");
        return new StepBuilder("dailySalesStep", batchJobRepository())
                .<DailySales, DailySales>chunk(10, dataTransactionManager)
                .allowStartIfComplete(true)
                .reader(dailySalesReader(dataDBSource))
                .writer(dailySalesWriter(dataDBSource))
                .build();
    }

    @Bean
    public DailySalesItemReader dailySalesReader(@Qualifier("dataDBSource") DataSource dataDBSource) {
        return new DailySalesItemReader(dataDBSource);
    }

    @Bean
    public JdbcBatchItemWriter<DailySales> dailySalesWriter(@Qualifier("dataDBSource") DataSource dataDBSource) {
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
    }
}
