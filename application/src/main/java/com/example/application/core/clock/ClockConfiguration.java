package com.example.application.core.clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfiguration {

        @Bean
        @Primary
        public Clock getClock(ZoneId zone) {
            return new ProxyClock(Clock.system(zone));
        }

        @Bean
        @Primary
        public ZoneId zoneId(@Value("${app.zoneId}") String zoneId) {
            return ZoneId.of(zoneId);
        }

}
