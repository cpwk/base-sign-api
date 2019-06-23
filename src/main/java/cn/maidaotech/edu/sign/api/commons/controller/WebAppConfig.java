package cn.maidaotech.edu.sign.api.commons.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-22 17:36
 **/
@Configuration
@EnableWebMvc
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor apiInterceptor() {
        return new DefaultInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
