package com.postechfiap_group130.techchallenge_fastfood.bdd.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.postechfiap_group130.techchallenge_fastfood.bdd.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        },
        monochrome = true
)
public class PaymentTest {
}