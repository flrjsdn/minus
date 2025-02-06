package com.hexa.muinus.common.batch.config;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import com.hexa.muinus.common.batch.reader.PreferenceItemReader;
import com.hexa.muinus.users.domain.preference.Preference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
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
public class PreferenceBatchConfig {

    private final PlatformTransactionManager metaTransactionManager;
    private final DataSource dataDBSource;
    private final JobRepository jobRepository;
    private final PreferenceItemReader preferenceItemReader;

    public PreferenceBatchConfig(
            @Qualifier("jobRepository") JobRepository jobRepository,
            @Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager,
            @Qualifier("dataDBSource") DataSource dataDBSource,
            PreferenceItemReader preferenceItemReader) {
        this.jobRepository = jobRepository;
        this.metaTransactionManager = metaTransactionManager;
        this.dataDBSource = dataDBSource;
        this.preferenceItemReader = preferenceItemReader;
    }

    @Bean
    public Job preferenceBatchJob() throws Exception {
        try {
            log.info("Preference Batch Job 생성");
            return new JobBuilder("PreferenceBatchJob", jobRepository)
                    .start(preferenceStep())
                    .build();
        } catch (Exception e) {
            log.error("배치 Job 생성 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.JOB_CREATION_FAILED, e);
        }
    }

    @Bean
    public Step preferenceStep() throws Exception {
        try {
            log.info("Preference Step 생성");
            return new StepBuilder("preferenceStep", jobRepository)
                    .<Preference, Preference>chunk(10, metaTransactionManager)
                    .allowStartIfComplete(true)
                    .reader(preferenceItemReader)
                    .writer(preferenceWriter(dataDBSource))
                    .build();
        } catch (Exception e) {
            log.error("배치 Step 생성 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.STEP_CREATION_FAILED, e);
        }
    }


    @Bean
    public JdbcBatchItemWriter<Preference> preferenceWriter(@Qualifier("dataDBSource") DataSource dataDBSource) {
        try {
            return new JdbcBatchItemWriterBuilder<Preference>()
                    .dataSource(dataDBSource)
                    .sql("""
                        INSERT INTO hexa.preference (user_no, item_id, score, updated_at)
                        VALUES (:userNo, :itemId, :score, :updatedAt)
                        ON DUPLICATE KEY UPDATE score = VALUES(score), updated_at = VALUES(updated_at)
                    """)
                    .beanMapped()
                    .build();
        } catch (Exception e) {
            log.error("Writer 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.WRITER_INITIALIZATION_FAILED, e);
        }
    }
}
