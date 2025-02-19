package me.artemiyulyanov;

import lombok.*;
import lombok.experimental.NonFinal;
import me.artemiyulyanov.web.WebSdkExpection;
import me.artemiyulyanov.web.endpoints.weather.WeatherCache;
import me.artemiyulyanov.web.endpoints.weather.WeatherData;
import me.artemiyulyanov.web.endpoints.weather.WeatherGetEndpoint;

import java.util.StringJoiner;

@Getter
public class WeatherSdk {
    private WeatherCache cache;
    private WeatherSdkPollingExecutor pollingExecutor;
    private String accessKey;

    public WeatherSdk(String accessKey, WeatherSdkMode mode) {
        this.accessKey = accessKey;
        this.cache = new WeatherCache();

        if (mode == WeatherSdkMode.POLLING) {
            this.pollingExecutor = new WeatherSdkPollingExecutor(this);
        } else {
            this.pollingExecutor = null;
        }
    }

    public WeatherData getWeather(String city) throws WebSdkExpection {
        WeatherData cachedData = getCache().get(city);
        if (cachedData != null && cachedData.isFresh()) {
            return cachedData;
        }

        WeatherData newData = createWeatherGetEndpoint(city).execute();
        getCache().put(city, newData);

        return newData;
    }

    // convenient for unit testing
    protected WeatherGetEndpoint createWeatherGetEndpoint(String city) throws WebSdkExpection {
        return WeatherGetEndpoint.builder()
                .accessKey(accessKey)
                .city(city)
                .build();
    }

    public static void main(String[] args) throws WebSdkExpection {
        WeatherSdk weatherSdk = new WeatherSdk("9fdb183cfcd8ef2c23b47ec6a1267dce", WeatherSdkMode.POLLING);

        WeatherData data = weatherSdk.getWeather("Tokyo");
        WeatherData data1 = weatherSdk.getWeather("Tokyo");
    }
}