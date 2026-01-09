package com.postechfiap_group130.techchallenge_fastfood.bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.engine.ConfigurationParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.postechfiap_group130.techchallenge_fastfood.TechchallengeFastfoodApplication;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-report/cucumber.html")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,
        value = "com.postechfiap_group130.techchallenge_fastfood.bdd")
@ConfigurationParameter(key = Constants.OBJECT_FACTORY_PROPERTY_NAME,
        value = "io.cucumber.spring.SpringFactory")
@ConfigurationParameter(key = Constants.JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME,
        value = "long")
public class CucumberTestRunner {

    private static ConfigurableApplicationContext applicationContext;

    @BeforeAll
    public static void beforeAll() {
        // Start the Spring Boot application before any tests run
        if (applicationContext == null) {
            applicationContext = SpringApplication.run(TechchallengeFastfoodApplication.class, "--server.port=0");
            System.out.println("Spring application started on port: " + 
                applicationContext.getEnvironment().getProperty("local.server.port"));
        }
    }
}
