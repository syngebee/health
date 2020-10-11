package com.itheima.health.security_config;


import com.itheima.health.security_service.HealthUserServiceDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfigurerAdapter  作用 ： 留给开发者  重写 框架的默认配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)//   开启权限注解配置
@EnableConfigurationProperties(JwtProperties.class)
public class HealthWebSecurityAdapter  extends WebSecurityConfigurerAdapter {

    //    spring ioc  容器 获取   UserDetailsService 接口实现类
    @Autowired
    HealthUserServiceDetailsImpl healthUserServiceDetailsImpl;
    @Autowired
    private JwtProperties properties;

    /**
     *   重写 登录  前端一般采用 ajax请求提交用户数据
     *    配置过滤器完成用户认证和授权
     *    以token形式返回
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().
                disable().
                addFilter(new JWTAuthenticationFilter(super.authenticationManager(),properties))
                .addFilter(new JWTAuthorizationFilter(super.authenticationManager(),properties))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用session
    }

}