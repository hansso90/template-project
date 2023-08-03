package com.example.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.OffsetDateTime;

import static com.example.application.api.OpenApiConstants.CLOCK_TAG;

@Tag(name = CLOCK_TAG)
@RestController
@Profile({"dev", "local", "test", "integration-test", "acc"})
@RequestMapping(path = "/api/clock")
public class ClockController
{
    private final Clock clock;

    @Autowired
    public ClockController(Clock clock) {
        this.clock = clock;
    }

    @Operation(summary = "Retrieve the clock and zone offset of the application")
    @GetMapping("/time")
    public OffsetDateTime getTime() {
        return OffsetDateTime.ofInstant(clock.instant(), clock.getZone());
    }
}
