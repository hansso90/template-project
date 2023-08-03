package com.example.testintegration;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class Constants {

    public static final ZoneId ZONE_ID = ZoneId.of("Europe/Amsterdam");
    public static final URI APP_ENDPOINT;

    static {
        try {
            APP_ENDPOINT = new URI(Optional.ofNullable(System.getenv("APP_ENDPOINT")).orElse("http://something:8080"));
        }catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

}
