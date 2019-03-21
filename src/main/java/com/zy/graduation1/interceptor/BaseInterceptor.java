package com.zy.graduation1.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BaseInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(identifyInterceptor());
        registry.addInterceptor(loginInterceptor()).excludePathPatterns("/static/**");
        registry.addInterceptor(autoInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //TODO 该位置和上传时的路径一致，也和config.json访问前缀一致
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/upload/");
    }

    @Bean
    public IdentifyInterceptor identifyInterceptor(){
        return new IdentifyInterceptor();
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public AutoInterceptor autoInterceptor(){
        return new AutoInterceptor();
    }
}
