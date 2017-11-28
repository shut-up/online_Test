package com.utils;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dengwenzhong on 17/7/8.
 */
public class CheckHelper {
    public static void checkNotNull(Object fieldValue, String desc) {
        if (null == fieldValue) {
            throw new CheckException(desc);
        }
    }

    public static void checkNotBlank(String field, String desc){
        if (Strings.isNullOrEmpty(field)){
            throw new CheckException(desc);
        }
    }

    public static void checkNotLessZero(Object fieldValue, String desc) {
        if (null == fieldValue || (null != fieldValue && (long)fieldValue < 0)) {
            throw new CheckException(desc);
        }
    }

    public static void remindNotNul(Object fieldValue, String desc) {
        if (null == fieldValue) {
            throw new RemindException(desc);
        }
    }

    public static void remindShouldBeNul(Object fieldValue, String desc) {
        if (fieldValue != null) {
            throw new RemindException(desc);
        }
    }

    public static void remindNotBlank(String field, String desc) {
        if (Strings.isNullOrEmpty(field)) {
            throw new RemindException(desc);
        }
    }

    public static void remindNotNul(Object fieldValue, String code, String desc) {
        if (null == fieldValue) {
            throw new RemindException(code, desc);
        }
    }

    public static void remindException(String code,String desc) {
        throw new RemindException(code,desc);
    }

    public static void checkRegex(String str, String regex, String desc) {
        if(Strings.isNullOrEmpty(str)){
            throw new CheckException(desc);
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()){
            throw new CheckException(desc);
        }

    }

}
