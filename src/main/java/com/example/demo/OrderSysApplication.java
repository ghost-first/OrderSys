package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("com.example")
@MapperScan("com.example.demo")
public class OrderSysApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    // 就是这里，下面就这样写就行了，不用去加一个配置类
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowCredentials(true).allowedOriginPatterns("*");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderSysApplication.class, args);
    }
}

/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
//public class SpringBootStartApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里要指向原先用main方法执行的Application启动类
//        return builder.sources(OrderSysApplication.class);
//    }
//}