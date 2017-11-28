package com.utils;

/**
 * Created by dengwenzhong on 17/7/8.
 */
public class RemindException extends RuntimeException {

    public String desc = RestCode.CD3[1];
    public String code = RestCode.CD3[0];

    public RemindException(String desc) {
        super(desc);
        this.desc = desc;
    }

    public RemindException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

}
