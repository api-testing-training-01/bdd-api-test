package org.fundacionjala.bdd.api.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty"},
        glue = {"org.fundacionjala.bdd.api"},
        features = {"src/test/resources/features"}
)
public class Runner  extends AbstractTestNGCucumberTests {

}
