package com.postechfiap_group130.techchallenge_fastfood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("test")
@PropertySource("classpath:application-test.properties")
public class MockConfig {
    // Configuration for test environment
} 