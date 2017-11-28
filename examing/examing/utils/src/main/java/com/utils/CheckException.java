package com.utils;

/**
 * Created by dengwenzhong on 17/7/8.
 */
public class CheckException extends RuntimeException {

    public String desc = RestCode.CD2[1];
    public String code = RestCode.CD2[0];

    public CheckException(String desc) {
        super(desc);
        this.desc = desc;
    }

}