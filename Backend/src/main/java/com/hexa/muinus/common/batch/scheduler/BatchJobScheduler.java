package com.hexa.muinus.common.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BatchJobScheduler {

    @Autowired
    @Qualifier("metaDBSource")
    private DataSource metaDBSource;

    @Autowired
    private JobLauncher jobLauncher;

    private final Job dailySalesJob;
    private final Job preferenceJob;

    public BatchJobScheduler(@Qualifier("DailySalesBatchJob") Job dailySalesJob,
                             @Qualifier("preferenceBatchJob") Job preferenceJob) {
        this.dailySalesJob = dailySalesJob;
        this.preferenceJob = preferenceJob;
    }

    // 매일 자정 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void runBatchJobAtMidnight() throws Exception {
        jobLauncher.run(dailySalesJob, new JobParameters());
        jobLauncher.run(preferenceJob, new JobParameters());
    }
}