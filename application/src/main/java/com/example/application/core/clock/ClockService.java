package com.example.application.core.clock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@Profile({"dev", "local", "test", "integration-test"})
@Slf4j
public class ClockService
{
    private final ProxyClock proxyClock;
    private final Clock clock;
    private boolean running = true;

    @Autowired
    public ClockService(Clock clock) {
        checkArgument(clock instanceof ProxyClock, "ClockService required clock to be swappable");
        proxyClock = (ProxyClock) clock;
        this.clock = proxyClock.getBaseClock();
    }

    /**
     * Pauses the clock.
     *
     * @see #start() to start the clock again
     */
    public synchronized void pause() {
        if (running) {
            proxyClock.setBaseClock(Clock.fixed(proxyClock.instant(), proxyClock.getZone()));
            running = false;
        }
    }

    /**
     * Starts the clock if it has been paused.
     *
     * @see #pause() to pause the clock
     */
    public synchronized void start() {
        if (!running) {
            final Duration offset = Duration.between(clock.instant(), proxyClock.instant());
            proxyClock.setBaseClock(Clock.offset(clock, offset));
            running = true;
        }
    }

    /**
     * Sets the clock forward one minute.
     */
    public synchronized void nextMinute() {
        final Instant instant = proxyClock.instant().truncatedTo(ChronoUnit.MINUTES);
        setInstant(instant.plus(1, ChronoUnit.MINUTES));
    }

    /**
     * Sets the clock to the provided instant.
     */
    public synchronized void setInstant(Instant instant) {
        if (instant.isBefore(proxyClock.instant())) {
            log.info("Moving backwards in time from {} to {}", proxyClock.instant(), instant);
        } else {
            log.info("Moving forwards in time from {} to {}", proxyClock.instant(), instant);
        }

        if (running) {
            final Duration offset = Duration.between(clock.instant(), instant);
            proxyClock.setBaseClock(Clock.offset(clock, offset));
        } else {
            proxyClock.setBaseClock(Clock.fixed(instant, proxyClock.getZone()));
        }
    }

}
