package com.demo.springbatch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class Scheduler {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;


    @Scheduled(cron = "0 26 16 * * ?")
    public void fixedRateSch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
         Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());
        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
    }
}






// https://dzone.com/articles/running-on-time-with-springs-scheduled-tasks
//    @Scheduled(cron = "0 * * * * ?")
//    @Scheduled(cron = "0 09 17 * * ? 2020")
//    @Scheduled(cron = "0/20 * * * * ?")
//                     0 0 12 * * ? 2017  At 12:00 pm (noon) every day during the year 2017: