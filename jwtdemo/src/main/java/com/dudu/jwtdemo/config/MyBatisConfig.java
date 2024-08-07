package com.dudu.jwtdemo.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.dudu.jwtdemo.mbg.mapper"})
public class MyBatisConfig {
}
