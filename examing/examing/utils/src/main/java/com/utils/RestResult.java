package com.utils;

import org.slf4j.Logger;

/**
 * Created by dengwenzhong on 17/7/8.
 */
public class RestResult<T> {
    public RestResult() {
    }

    public RestResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public RestResult(String code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public void remind(String desc) {
        this.remind(desc, null);
    }

    public void remind(String desc, Logger log) {
        this.code = RestCode.CD3[0];
        this.desc = RestCode.CD3[1] + desc;
        if (log != null) {
            log.warn("remind , desc : {}", desc);
        }
    }

    public void errorParam(String desc) {
        this.errorParam(desc, null);
    }

    public void errorParam(String desc, Logger log) {
        this.code = RestCode.CD2[0];
        this.desc = RestCode.CD2[1] + desc;
        if (log != null) {
            log.warn("errorParam , desc : {}", desc);
        }
    }

    public void custom(String code, String desc, Logger log) {
        this.code = code;
        this.desc = desc;
        if (log != null) {
            log.warn("custom , code : {} , desc : {}", code, desc);
        }
    }


    public String code = RestCode.CD1[0];
    public String desc = RestCode.CD1[1];
    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", list=" + data +
                '}';
    }
}
