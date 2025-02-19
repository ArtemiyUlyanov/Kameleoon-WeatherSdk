package me.artemiyulyanov;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.artemiyulyanov.web.endpoints.weather.WeatherData;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public class WeatherSdkPollingExecutor {
    private WeatherSdk instance;
    private ScheduledExecutorService pollingExecutor;

    public WeatherSdkPollingExecutor(WeatherSdk instance) {
        this.instance = instance;
        this.pollingExecutor = Executors.newScheduledThreadPool(1);

        startPolling();
    }

    private void startPolling() {
        pollingExecutor.scheduleAtFixedRate(() -> {
            System.out.println("Polling weather data...");
            List<String> cities = instance.getCache().getAllCities();
            for (String city : cities) {
                try {
                    WeatherData newData = instance.getWeather(city);
                    instance.getCache().put(city, newData);
                } catch (Exception e) {
                    System.err.println("Failed to update weather for " + city + ": " + e.getMessage());
                }
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

    public void shutdown() {
        if (pollingExecutor != null) {
            pollingExecutor.shutdown();
        }
    }
}