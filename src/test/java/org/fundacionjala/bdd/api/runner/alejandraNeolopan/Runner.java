package org.fundacionjala.bdd.api.runner.alejandraNeolopan;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;

@CucumberOptions(
        plugin = {"pretty"},
        glue = {"org.fundacionjala.bdd.api"},
        features = {"src/test/resources/features"}
)
public class Runner extends AbstractTestNGCucumberTests {
    @BeforeTest
    public void beforeAllScenarios() {
        //For db conexions
        //For open browsers
    }
}
