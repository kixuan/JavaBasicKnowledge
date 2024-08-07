package com.dudu.jwtdemo.config;

import com.dudu.jwtdemo.interceptor.JWTInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class JWTInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private JWTInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login");
    }
}
