package me.artemiyulyanov.web;

import lombok.*;
import me.artemiyulyanov.web.endpoints.weather.WeatherData;
import me.artemiyulyanov.web.endpoints.weather.WeatherGetEndpoint;

@Getter
@Setter
public class WebSdkExpection extends Exception {
    public WebSdkExpection(String message) {
        super(message);
    }

    public WebSdkExpection(String message, Throwable cause) {
        super(message, cause);
    }
}