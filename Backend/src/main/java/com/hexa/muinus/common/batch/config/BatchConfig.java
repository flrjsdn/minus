package com.hexa.muinus.common.batch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class BatchConfig {
    @Bean
    public DataSourceScriptDatabaseInitializer batchSchemaInitializer(@Qualifier("metaDBSource") DataSource metaDBSource) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(List.of("classpath:schema-batch.sql"));
        settings.setMode(DatabaseInitializationMode.ALWAYS);

        return new DataSourceScriptDatabaseInitializer(metaDBSource, settings);
    }
}
