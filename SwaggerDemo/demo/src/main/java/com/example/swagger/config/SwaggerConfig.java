package com.example.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig {

    // 配置Swagger的Docket的Bean实例
    @Bean
    public Docket docket(Environment environment) {
        // 设置要显示swagger的环境
        Profiles of = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        boolean flag = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("kixuan")
                // 是否启用Swagger，如果为false，则Swagger不能在浏览器中访问
                // 多环境情况，通过 enable() 接收此参数判断是否要显示
                .enable(flag)
                .select()
                // RequestHandlerSelectors：配置要扫描接口的方式
                // basePackage：指定要扫描的包
                // any()  扫描所有，项目中的所有接口都会被扫描到
                // none()  不扫描接口
                // withMethodAnnotation()：通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
                // withClassAnnotation()：通过类上的注解扫描，如withClassAnnotation(RestController.class)只扫描有RestController注解的类中的接口
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/admin开头的接口
                // .paths(PathSelectors.ant("/admin/.*"))
                .build();
    }

    // 配置多个groupName  --  通过配置多个docket
    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
    }


    //配置文档信息
    // 看源码发现不能通过构造器传参，只能通过链式编程的方式传参
    private ApiInfo apiInfo() {
        // 作者信息
        Contact contact = new Contact("kixuan", "https://kixuan.github.io/", "xxxx@qq.com");
        return new ApiInfo(
                "Swagger学习日志", // 标题
                "学习演示如何配置Swagger", // 描述
                "v1.0", // 版本
                "https://www.bilibili.com/video/BV1Y441197Lw/?spm_id_from=333.337.search-card.all.click", // 组织链接 Terms of service
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

}
