package com.hexa.muinus.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

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

        return DataSourceBuilder.create().build();
    }


    @Bean(name="metaTransactionManager")
    public PlatformTransactionManager metaTransactionManager() {
        return new DataSourceTransactionManager(metaDBSource());
    }

    @Bean(name = "metaEntityManager")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("metaDBSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.hexa.muinus.common.batch") // ✅ Users 엔티티 제외
                .persistenceUnit("batch")
                .build();
    }
}