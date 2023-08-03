package com.example.testintegration.stepdefinitions;

import com.example.testintegration.core.clock.ClockService;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockStepDefinition {

    @Autowired
    private ClockService clockService;

    @Given("application is live")
    public void givenApplicationIsLive() {
        assertThat(clockService.getTime()).isNotNull();
    }
}
