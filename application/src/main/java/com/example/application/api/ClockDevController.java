package com.example.application.api;

import com.example.application.core.clock.ClockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;

import static com.example.application.api.OpenApiConstants.CLOCK_TAG;

@Tag(name = CLOCK_TAG)
@RestController
@Profile({"dev", "local", "test", "integration-test", "acc"})
@RequestMapping(path = "/api/clock")
public class ClockDevController
{
    private final ClockService clockService;
    private final Clock clock;

    @Autowired
    public ClockDevController(ClockService clockService, Clock clock) {
        this.clockService = clockService;
        this.clock = clock;
    }

    @Operation(summary = "Pause the clock of the app, keeping the time fixed at one point in time")
    @PostMapping("/pause")
    public Instant pauseClock() {
        clockService.pause();
        return clock.instant();
    }

    @Operation(summary = "Start the clock, so that it ticks with one second per second")
    @PostMapping("/start")
    public Instant startClock() {
        clockService.start();
        return clock.instant();
    }

    @Operation(summary = "Set the clock to a specific instant in time")
    @PutMapping("/time")
    public void setTime(@Parameter(description = "Time to set in the DSO", required = true) @RequestBody Instant instant) {
        clockService.setInstant(instant);
    }

    @Operation(summary = "Advance the clock to the start of the next minute")
    @PostMapping("/nextMinute")
    public Instant nextMinute() {
        clockService.nextMinute();
        return clock.instant();
    }
}
