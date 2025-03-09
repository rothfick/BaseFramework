package com.peliQAn.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG runner for Cucumber tests
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.peliQAn.framework.stepdefinitions",
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html"
        },
        tags = "@ui or @api or @regression or @smoke",
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    
    /**
     * Runs tests in parallel
     * 
     * @return Test data provider
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}