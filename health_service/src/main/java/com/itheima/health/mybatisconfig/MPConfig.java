package com.itheima.health.mybatisconfig;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {
    @Bean
    public PaginationInterceptor createPaginationInterceptor(){
        return  new PaginationInterceptor(); //  mp  分页插件对象 配置到ioc容器中
    }
}
