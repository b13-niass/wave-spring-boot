package com.example.wavespringboot.config;

import jakarta.servlet.Filter;
import com.example.wavespringboot.web.filter.ResponseFormattingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public Filter responseFormattingFilter() {
        return new ResponseFormattingFilter();
    }
}
