package com.postechfiap_group130.techchallenge_fastfood.config;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.handler.GlobalExceptionHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@TestConfiguration
//@EnableWebMvc
//@ComponentScan(basePackages = {
//        "com.postechfiap_group130.techchallenge_fastfood.adapters.in.rest.controller",
//        "com.postechfiap_group130.techchallenge_fastfood.adapters.in.rest.handler"
//})
//public class TestWebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public GlobalExceptionHandler globalExceptionHandler() {
//        return new GlobalExceptionHandler();
//    }
//}