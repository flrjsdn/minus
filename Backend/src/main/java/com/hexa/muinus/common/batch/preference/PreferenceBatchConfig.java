package com.hexa.muinus.common.batch.preference;

import com.hexa.muinus.users.domain.preference.Preference;
import com.hexa.muinus.users.domain.preference.repository.PreferenceRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Configuration
@EnableBatchProcessing(dataSourceRef = "metaDBSource", transactionManagerRef = "metaTransactionManager")
public class PreferenceBatchConfig {

    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource metaDBSource;
    private final DataSource dataDBSource;
    private final PreferenceReader preferenceReader;

    public PreferenceBatchConfig(
            @Qualifier("metaTransactionManager") PlatformTransactionManager platformTransactionManager,
            @Qualifier("metaDBSource") DataSource metaDBSource,
            @Qualifier("dataDBSource") DataSource dataDBSource,
            @Qualifier("dataEntityManager") EntityManagerFactory dataEntityManagerFactory,
            PreferenceReader preferenceReader) {
        this.platformTransactionManager = platformTransactionManager;
        this.metaDBSource = metaDBSource;
        this.dataDBSource = dataDBSource;
        this.preferenceReader = preferenceReader;
    }

    @Bean
    public JobRepository preferenceJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(metaDBSource);
        factory.setTransactionManager(platformTransactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        factory.setTablePrefix("BATCH_");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public Job preferenceBatchJob() throws Exception {
        log.info("Preference Batch Job Started");
        return new JobBuilder("preferenceBatchJob", preferenceJobRepository())
                .start(preferenceStep())
                .build();
    }

    @Bean
    public Step preferenceStep() throws Exception {
        log.info("Preference Step Started");
        return new StepBuilder("preferenceStep", preferenceJobRepository())
                .<Preference, Preference>chunk(10, platformTransactionManager)
                .allowStartIfComplete(true)
                .reader(preferenceReader)
                .writer(preferenceWriter(dataDBSource))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Preference> preferenceWriter(@Qualifier("dataDBSource") DataSource dataDBSource) {
        return new JdbcBatchItemWriterBuilder<Preference>()
                .dataSource(dataDBSource)
                .sql("""
                INSERT INTO preference (user_no, item_id, score, calculated_date)
                VALUES (:userNo, :itemId, :score, :calculatedDate)
                ON DUPLICATE KEY UPDATE score = VALUES(score), calculated_date = VALUES(calculatedDate)
            """)
                .beanMapped()
                .build();
    }
}
