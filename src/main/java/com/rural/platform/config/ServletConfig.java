package com.rural.platform.config;

import com.rural.platform.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 原生Servlet的配置类:
 */
@Configuration
public class ServletConfig {

    //注入redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注册原生Servlet的Filter
     */
    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilter(){

        FilterRegistrationBean<SecurityFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        //创建SecurityFilter对象
        SecurityFilter securityFilter = new SecurityFilter();
        //给SecurityFilter对象注入redis模板
        securityFilter.setRedisTemplate(redisTemplate);
        //注册SecurityFilter
        filterRegistrationBean.setFilter(securityFilter);
        //配置SecurityFilter拦截所有请求
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
