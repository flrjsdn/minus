package com.hexa.muinus.common.batch.config;

import com.hexa.muinus.common.batch.exeption.BatchErrorCode;
import com.hexa.muinus.common.batch.exeption.BatchProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackages = "com.hexa.muinus.common.batch",
        entityManagerFactoryRef = "metaEntityManager",
        transactionManagerRef = "metaTransactionManager"
)
public class MetaDBConfig {

    @Bean(name="metaDBSource")
    @ConfigurationProperties(prefix = "spring.datasource-meta")
    public DataSource metaDBSource() {
        try {
            log.info("MetaDB DataSource 초기화 시작");
            return DataSourceBuilder.create().build();
        } catch (Exception e) {
            log.error("메타 데이터 DB 연결 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.DATABASE_CONNECTION_FAILED, e);
        }
    }


    @Bean(name="metaTransactionManager")
    public PlatformTransactionManager metaTransactionManager() {
        try {
            log.info("MetaDB TransactionManager 초기화 시작");
            return new DataSourceTransactionManager(metaDBSource());
        } catch (Exception e) {
            log.error("트랜잭션 매니저 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.TRANSACTION_MANAGER_INIT_FAILED, e);
        }
    }

    @Bean(name = "metaEntityManager")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("metaDBSource") DataSource dataSource) {
        try {
            log.info("MetaDB EntityManagerFactory 초기화 시작");
            return builder
                    .dataSource(dataSource)
                    .packages("com.hexa.muinus.common.batch")
                    .persistenceUnit("batch")
                    .build();
        } catch (Exception e) {
            log.error("EntityManager 초기화 실패: {}", e.getMessage());
            throw new BatchProcessingException(BatchErrorCode.ENTITY_MANAGER_INIT_FAILED, e);
        }
    }
}