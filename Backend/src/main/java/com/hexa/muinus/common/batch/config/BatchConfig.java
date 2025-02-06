package com.hexa.muinus.common.batch.config;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
public class BatchConfig {

    private final DataSource metaDBSource;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public DataSourceScriptDatabaseInitializer batchSchemaInitializer(@Qualifier("metaDBSource") DataSource metaDBSource) {
        try {
            log.info("배치 스키마 초기화 시작 (schema-batch.sql 실행)");

            DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
            settings.setSchemaLocations(List.of("classpath:schema-batch.sql"));
            settings.setMode(DatabaseInitializationMode.ALWAYS);

            return new DataSourceScriptDatabaseInitializer(metaDBSource, settings);
        } catch (Exception e) {
            log.error("배치 스키마 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.SCHEMA_INITIALIZATION_FAILED, e);
        }
    }

    public BatchConfig(
            @Qualifier("metaDBSource") DataSource metaDBSource,
            @Qualifier("metaTransactionManager") PlatformTransactionManager platformTransactionManager) {
        this.metaDBSource = metaDBSource;
        this.platformTransactionManager = platformTransactionManager;
    }
}
