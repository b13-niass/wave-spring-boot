package com.example.wavespringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://cheikh-ibrahima-dieng.up.railway.app/api/v1")  // Swagger UI URL or other clients
                .allowedOrigins("http://cheikh-ibrahima-dieng.up.railway.app/api/v1/")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
