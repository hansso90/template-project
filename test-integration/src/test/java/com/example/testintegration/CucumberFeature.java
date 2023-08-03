package com.example.testintegration;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;

import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;


import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features/")
@SpringBootTest(classes = {Application.class})
public class CucumberFeature
{
}
