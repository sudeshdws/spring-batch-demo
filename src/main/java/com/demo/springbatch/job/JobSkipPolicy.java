package com.demo.springbatch.job;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.annotation.Autowired;

public class JobSkipPolicy implements SkipPolicy {

    @Autowired
    WriterJob  writerJob;

    @Override
    public boolean shouldSkip(Throwable throwable, int failedCount) throws SkipLimitExceededException {
        writerJob.failCount(failedCount);
       return (failedCount >= 5) ? false : true;

    }
}
