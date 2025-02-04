package com.hexa.muinus.common.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Component
public class BatchJobScheduler {

    @Autowired
    @Qualifier("metaDBSource")
    private DataSource metaDBSource;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("DailySalesBatchJob")
    private Job dailySalesJob;

    @Autowired
    @Qualifier("preferenceBatchJob")
    private Job preferenceJob;

    // 매일 자정 30초에 실행 (00:00:30)
    @Scheduled(cron = "0 33 7 * * ?")
    public void runBatchJobAtMidnight() throws Exception {
        jobLauncher.run(dailySalesJob, new JobParameters());
        jobLauncher.run(preferenceJob, new JobParameters());
    }
}