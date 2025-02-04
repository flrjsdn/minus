package com.hexa.muinus.common.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BatchJobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job dailySalesJob;

    // 매일 자정 5분에 실행 (00:05)
    @Scheduled(cron = "10 32 14 * * ?")
    public void runBatchJobAtMidnight5() throws Exception {
        jobLauncher.run(dailySalesJob, new JobParameters());
    }
}