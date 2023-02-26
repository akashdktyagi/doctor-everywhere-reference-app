package com.automationfraternity.cucumber;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = {
                "classpath:features"
        },
        glue = {
                "com.automationfraternity"
        },
        tags = "",
        plugin = {"pretty",
                "com.bjb.xray.listener.XRayListener",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
        }
)
@RunWith(Cucumber.class)
public class RunIT {

}