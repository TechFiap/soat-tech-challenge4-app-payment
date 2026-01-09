package com.postechfiap_group130.techchallenge_fastfood.bdd.config;

import com.postechfiap_group130.techchallenge_fastfood.TechchallengeFastfoodApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = TechchallengeFastfoodApplication.class
)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableAutoConfiguration
public class CucumberConfig {
    // This class enables Cucumber to use Spring's dependency injection and test configuration
}
