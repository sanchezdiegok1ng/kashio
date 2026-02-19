package com.co.test.runners;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        tags = "@tag",
        glue = "com.co.test.stepsdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class Runner {
}
