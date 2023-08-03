package com.example.testintegration.core.clock;

import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.testintegration.Constants.APP_ENDPOINT;
import static com.example.testintegration.Constants.ZONE_ID;
import static io.restassured.RestAssured.given;


@Service
public class ClockService {

    public ZonedDateTime getTime() {
        URI requestUri = APP_ENDPOINT.resolve("/api/clock/time");
        final Response timeResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(requestUri);
        timeResponse.then().statusCode(HttpStatus.OK.value()).assertThat();

        return parseSpikeTime(timeResponse.asString());
    }
    private ZonedDateTime parseSpikeTime(String spikeTime) {
        return OffsetDateTime.parse(spikeTime.substring(1, spikeTime.length() - 1), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .toZonedDateTime()
                .withZoneSameInstant(ZONE_ID);
    }

}
