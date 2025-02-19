package me.artemiyulyanov.web.endpoints.weather;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private static final long FRESHNESS_DURATION = 10 * 60 * 1000;

    private Weather weather;
    private Temperature temperature;
    private int visibility;
    private long timestamp;
    private Wind wind;
    private Sys sys;
    private int timezone;
    private String name;

    public boolean isFresh() {
        return System.currentTimeMillis() - timestamp < FRESHNESS_DURATION;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {
        private String main, description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Temperature {
        private double temp, feelsLike, tempMin, tempMax, pressure, humidity, seaLevel, grndLevel;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Wind {
        private double speed, degree;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sys {
        private long sunrise, sunset;
    }
}