package com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by dengwenzhong on 17/7/8.
 */
public interface BaseDoing<T> {

    default void showRequestBody(Object body, ObjectMapper objectMapper, Logger log) throws JsonProcessingException {
        if (body != null && objectMapper != null) {
            log.info("request body : {}", objectMapper.writeValueAsString(body));
        }
    }

    /**
     * 请求参数记录
     *
     * @param request
     * @param log
     */
    default void showParams(HttpServletRequest request, Logger log) throws IOException {
        if (request != null && "GET".equals(request.getMethod())) {
            Enumeration<String> paramsEnumeration = request.getParameterNames();
            StringBuilder parameterStringBuffer = new StringBuilder("params => { ");
            while (paramsEnumeration.hasMoreElements()) {
                String name = paramsEnumeration.nextElement();
                parameterStringBuffer.append(name).append(" : ").append(request.getParameter(name)).append(" ,");
            }
            parameterStringBuffer.deleteCharAt(parameterStringBuffer.length() - 1);
            parameterStringBuffer.append(" }");
            log.info(parameterStringBuffer.toString());
        }
    }

    default void showErrorLog(Exception e, Logger log) {
        this.showErrorLog(null, e, log);
    }

    default void showErrorLog(RestResult restResult, Exception e, Logger log) {
        if (restResult != null) {
            restResult.setCode(RestCode.CD0[0]);
            restResult.setDesc(RestCode.CD0[1]);
        }
        log.error(e.getMessage());
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            log.error(stackTraceElement.toString());
        }
        e.printStackTrace();
    }

    default void showReturnData(RestResult jr, ObjectMapper objectMapper, Logger log) {
        if (objectMapper != null) {
            try {
                if (jr.code.equals(RestCode.CD0[0])) {
                    log.error("response : {}", objectMapper.writeValueAsString(jr));
                } else if (jr.code.equals(RestCode.CD1[0])) {
                    log.info("response : {}", objectMapper.writeValueAsString(jr));
                } else {
                    log.warn("response : {}", objectMapper.writeValueAsString(jr));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    default void showVisitor(Object merchantDto, ObjectMapper objectMapper, Logger log) throws JsonProcessingException {
        if (objectMapper != null && merchantDto != null) {
            log.info("visitor : {} ", objectMapper.writeValueAsString(merchantDto));
        }
    }

    default void errorCheckLog(Logger log, CheckException e, RestResult<T> restResult) {
        restResult.setCode(e.code);
        restResult.setDesc(e.desc);
        this.showWarnLog(log, restResult);
    }

    default void errorRemindLog(Logger log, RemindException e, RestResult<T> restResult) {
        restResult.setCode(e.code);
        restResult.setDesc(e.desc);
        this.showWarnLog(log, restResult);
    }

    default void showWarnLog(Logger log, RestResult restResult) {
        log.warn("code : {} , desc : {}", restResult.code, restResult.desc);
    }

    /**
     * 错误日志输出
     *
     * @param log
     * @param e
     * @param jr
     */
    default void errorLog(Logger log, Exception e, RestResult<T> jr) {
        jr.setCode(RestCode.CD0[0]);
        jr.setDesc(RestCode.CD0[1]);
        this.errorLog(log, e);
    }


    default void errorLog(Logger log, Exception e) {
        log.error(e.getMessage());
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            log.error(stackTraceElement.toString());
        }
        e.printStackTrace();
    }

    default void showSql(Map<String, Object> params, String sqlName, String sql, Logger log) {
        // 输出 sql
        Set<String> paramsKeys = params.keySet();
        Iterator<String> keys = paramsKeys.iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            sql = sql.replace(":" + key, params.get(key).toString());
        }

        log.info("{} : {}", sqlName, sql);
    }


}

