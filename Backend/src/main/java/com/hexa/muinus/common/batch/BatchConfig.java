package com.hexa.muinus.common.batch;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class BatchConfig extends DefaultBatchConfigurer {

    private final DataSource metaDBSource;

    public BatchConfig(@Qualifier("metaDBSource") DataSource metaDBSource) {
        this.metaDBSource = metaDBSource;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        // 🟢 Spring Batch의 DataSource를 metaDB로 강제 지정
        super.setDataSource(metaDBSource);
    }
}