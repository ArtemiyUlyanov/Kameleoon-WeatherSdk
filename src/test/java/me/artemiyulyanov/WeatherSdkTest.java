package me.artemiyulyanov;

import me.artemiyulyanov.web.WebSdkExpection;
import me.artemiyulyanov.web.endpoints.weather.WeatherCache;
import me.artemiyulyanov.web.endpoints.weather.WeatherData;
import me.artemiyulyanov.web.endpoints.weather.WeatherGetEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherSdkTest {
    @InjectMocks
    @Spy
    private WeatherSdk weatherSdk;

    @Mock
    private WeatherCache cache;

    @Mock
    private WeatherGetEndpoint weatherGetEndpoint;

    @BeforeEach
    void setUp() throws WebSdkExpection {
        MockitoAnnotations.openMocks(this);

        weatherSdk = new WeatherSdk("9fdb183cfcd8ef2c23b47ec6a1267dce", WeatherSdkMode.ON_DEMAND);
        weatherSdk = spy(weatherSdk);

        doReturn(cache).when(weatherSdk).getCache();
        doReturn(weatherGetEndpoint).when(weatherSdk).createWeatherGetEndpoint("Tokyo");
    }


    // check if there is a referring to an endpoint when data is fresh and no need to fetch it
    @Test
    void testGetWeather_CacheHit() throws WebSdkExpection {
        WeatherData mockWeatherData = mock(WeatherData.class);
        when(mockWeatherData.isFresh()).thenReturn(true);

        when(cache.get("Tokyo")).thenReturn(mockWeatherData);

        WeatherData result = weatherSdk.getWeather("Tokyo");

        assertEquals(mockWeatherData, result);
        verify(cache, times(1)).get("Tokyo");
        verifyNoInteractions(weatherGetEndpoint);
    }

    // check if there is a fetching to a server when the city is not in the cache
    @Test
    void testGetWeather_CacheMiss() throws WebSdkExpection {
        WeatherData mockWeatherData = mock(WeatherData.class);
        when(cache.get("Tokyo")).thenReturn(null);

        when(weatherGetEndpoint.execute()).thenReturn(mockWeatherData);

        WeatherData result = weatherSdk.getWeather("Tokyo");

        assertEquals(mockWeatherData, result);
        verify(cache).put("Tokyo", mockWeatherData);
        verify(weatherGetEndpoint).execute();
    }
}
