package me.artemiyulyanov.web.endpoints.weather;

import lombok.*;
import me.artemiyulyanov.web.WebRequestProcessingService;
import me.artemiyulyanov.web.WebSdkExpection;
import me.artemiyulyanov.web.endpoints.Endpoint;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherGetEndpoint implements Endpoint<WeatherData, WeatherDataParser> {
    private String accessKey, city;

    @Override
    public WeatherData execute() throws WebSdkExpection {
        return WebRequestProcessingService.get(
                        getEndpoint(),
                        Map.of("appid", accessKey, "q", city),
                        getParser());
    }

    @Override
    public WeatherDataParser getParser() {
        return new WeatherDataParser();
    }

    @Override
    public String getEndpoint() {
        return "https://api.openweathermap.org/data/2.5/weather";
    }
}