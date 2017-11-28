package com.utils;

import org.slf4j.Logger;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengwenzhong on 17/7/8.
 */
@FunctionalInterface
public interface RestDoing<T> extends BaseDoing<T> {

    @Deprecated
    default RestResult<T> go(Logger log) {
        return this.invoke(null, null, null, log);
    }

    default RestResult<T> go(HttpServletRequest r, Logger log) {
        return this.invoke(null, null, r, log);
    }

    default RestResult<T> go(Object inputData, Logger log) {
        return this.invoke(inputData, null, null, log);
    }

    default RestResult<T> go(Object visitor, HttpServletRequest request, Logger log) {
        return this.invoke(null, visitor, request, log);
    }

    default RestResult<T> go(Object inputData, Object visitor, HttpServletRequest request, Logger log) {
        return this.invoke(inputData, visitor, request, log);
    }

    default RestResult<T> invoke(Object inputData, Object visitor, HttpServletRequest request, Logger log) {
        Long startTime = System.currentTimeMillis();
        log.info("############# current run method <<< {} >>> #####################################", Thread.currentThread().getStackTrace()[3].getMethodName());
        RestResult<T> jr = new RestResult<>();
        boolean isSuccess = true;
        try {
            this.showVisitor(visitor, Utils.objectMapper, log);
            this.showParams(request, log);
            this.showRequestBody(inputData, Utils.objectMapper, log);
            this.service(jr);
        } catch (CheckException e) {
            isSuccess = false;
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException ignored) {
            }
            this.errorCheckLog(log, e, jr);
        } catch (RemindException e) {
            isSuccess = false;
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException ignored) {
            }
            this.errorRemindLog(log, e, jr);
        } catch (Exception e) {
            isSuccess = false;
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException ignored) {
            }
            this.errorLog(log, e, jr);
        } finally {
            if (isSuccess) {
                this.showReturnData(jr, Utils.objectMapper, log);
            }
            log.info("############# current run method <<< {} >>> , processing time {} ms #############", Thread.currentThread().getStackTrace()[3].getMethodName(), (System.currentTimeMillis() - startTime));
        }
        return jr;
    }


    void service(RestResult<T> restResult) throws Exception;

}

