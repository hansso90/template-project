package com.example.application.core.clock;

import com.google.common.base.Objects;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Getter
public final class ProxyClock extends Clock {

    private Clock baseClock;

    public ProxyClock(Clock baseClock) {
        this.baseClock = baseClock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZoneId getZone() {
        return baseClock.getZone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant instant() {
        return baseClock.instant();
    }

    Clock getBaseClock() {
        return baseClock;
    }

    void setBaseClock(Clock baseClock) {
        this.baseClock = baseClock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(getBaseClock().getZone())) {
            return this;
        } else {
            return new ProxyClock(Clock.system(zone));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProxyClock that = (ProxyClock) o;
        return Objects.equal(baseClock, that.baseClock);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), baseClock);
    }
}
