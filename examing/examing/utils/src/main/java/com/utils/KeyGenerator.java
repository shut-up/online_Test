package com.utils;

/**
 * Created by dengwenzhong on 17/8/2.
 */
/**
 * 主键生成器.
 *
 * @author zhangliang
 */
public interface KeyGenerator {
    /**
     * 生成主键.
     *
     * @return 自动生成的主键
     */
    Number generateKey();
}