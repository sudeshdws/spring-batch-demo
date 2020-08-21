//package com.demo.springbatch.job;
//
//import javax.persistence.PersistenceException;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.batch.core.step.skip.SkipLimitExceededException;
//import org.springframework.batch.core.step.skip.SkipPolicy;
//import org.springframework.http.converter.json.GsonBuilderUtils;
//
//public class ConstraintViolationExceptionSkipper implements SkipPolicy {
//
//    private int skipLimit;
//
//    @Override
//    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
//        System.out.println("=======================================Here in ConstraintViolationExceptionSkipper ===============");
//        if(t instanceof javax.validation.ConstraintViolationException ||
//                t.getCause() instanceof ConstraintViolationException) {
//            return true;
//        }
//        return false;
//    }
//
//    public void setSkipLimit(int skipLimit) {
//        this.skipLimit = skipLimit;
//    }
//}