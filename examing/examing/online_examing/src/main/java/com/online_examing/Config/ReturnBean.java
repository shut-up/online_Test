package com.online_examing.Config;

import com.utils.DefaultKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: walton
 * @Description:
 * @Createtime: 2017/12/4
 */
@Configuration
public class ReturnBean {
    /**
    *@Description: 生成主键
    *@Date: 2017/12/4
    */
    @Bean
    DefaultKeyGenerator defaultKeyGenerator(){
        return new DefaultKeyGenerator();
    }
}
