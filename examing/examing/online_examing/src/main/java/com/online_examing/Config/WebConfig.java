package com.online_examing.Config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
*@Description: 解决跨域请求
*@Date: 2017/12/4
*/
@Configuration
@EnableWebMvc
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter {


    /*@Autowired
     private AuthInterceptor authInterceptor;
 */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //重新配置 json 的输出格式 驼峰标示 转为 下划线(helloWorld => hello_world)
//        builder.propertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(builder.build());
        converters.add(mappingJackson2HttpMessageConverter);


    }



    /*@Override

    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        //registry.addInterceptor(simpleCORSInterceptor).addPathPatterns(Routes.PREAPI+"*//**//**");

     registry.addInterceptor(authInterceptor).addPathPatterns(Routes.PREAPI+"*//**//**").
     excludePathPatterns(
     Routes.USER_LOGIN,
     Routes.USER_WEIXIN_REGISTER,
     Routes.USER_LOGIN_OPENID,
     Routes.USER_WEB_REGISTER,Routes.WX_GET_CODE_CALLBACK ,Routes.MESSAGE_VERIFY_CODE_UNAUTH);

     }*/

//    *//**
//     //     * 允许跨域请求
//     //     * @param registry
//     //     *//*
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("*//**").allowedHeaders("*")
//        .allowedMethods("*")
//        .allowedOrigins("*")
//        .allowCredentials(true)
//        .exposedHeaders("access-control-allow-headers",
//                "access-control-allow-methods",
//                "access-control-allow-origin",
//                "access-control-max-age",
//                "X-Frame-Options");
//    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
