package me.artemiyulyanov.web.endpoints;

import me.artemiyulyanov.web.WebResponseParser;
import me.artemiyulyanov.web.WebSdkExpection;
import me.artemiyulyanov.web.endpoints.weather.WeatherDataParser;

public interface Endpoint<S, T extends WeatherDataParser> {
    S execute() throws WebSdkExpection;
    T getParser();

    String getEndpoint();
}