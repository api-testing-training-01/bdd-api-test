package org.fundacionjala.bdd.api.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

@CucumberOptions(
        plugin = {"pretty"},
        glue = {"org.fundacionjala.bdd.api"},
        features = {"src/test/resources/features"}
)
public class Runner extends AbstractTestNGCucumberTests {

    @BeforeTest
    public void beforeAllScenarios() {
        // Code executed before features execution.
    }

    @AfterTest
    public void afterAllScenarios() {
        // Code executed after features execution.
    }
}
