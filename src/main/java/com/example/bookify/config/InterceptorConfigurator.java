package com.example.bookify.config;

import com.example.bookify.interceptors.IpBlacklistInterceptor;
import com.example.bookify.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurator implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggingInterceptor());
//        registry.addInterceptor(new IpBlacklistInterceptor());
    }
}
