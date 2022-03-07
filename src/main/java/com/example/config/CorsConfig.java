package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 解决跨域问题
     *
     * @author Peter Hai
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("addCorsMapping");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
        System.out.println("addCorsMapping 结束");
    }

}