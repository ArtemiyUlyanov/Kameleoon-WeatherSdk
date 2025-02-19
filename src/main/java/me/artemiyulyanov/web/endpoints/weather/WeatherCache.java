package me.artemiyulyanov.web.endpoints.weather;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WeatherCache {
    private static final int MAX_SIZE = 10;
    private final Map<String, WeatherData> cache;

    public WeatherCache() {
        this.cache = new LinkedHashMap<>(MAX_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, WeatherData> eldest) {
                return size() > MAX_SIZE;
            }
        };
    }

    public WeatherData get(String city) {
        return cache.get(city);
    }

    public void put(String city, WeatherData data) {
        cache.put(city, data);
    }

    public List<String> getAllCities() {
        return new ArrayList<>(cache.keySet());
    }
}