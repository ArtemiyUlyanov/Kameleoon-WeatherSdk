package me.artemiyulyanov;

import me.artemiyulyanov.web.endpoints.weather.WeatherData;
import me.artemiyulyanov.web.endpoints.weather.WeatherDataParser;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherDataParserTest {
    private WeatherDataParser weatherDataParser;

    @BeforeEach
    void setUp() {
        weatherDataParser = new WeatherDataParser();
    }

    @Test
    void testParse_ValidJson() {
        String validJson = "{\n" +
                "  \"weather\": [{\n" +
                "    \"main\": \"Clear\",\n" +
                "    \"description\": \"clear sky\"\n" +
                "  }],\n" +
                "  \"main\": {\n" +
                "    \"temp\": 298.72,\n" +
                "    \"feels_like\": 298.0,\n" +
                "    \"temp_min\": 298.0,\n" +
                "    \"temp_max\": 299.0,\n" +
                "    \"pressure\": 1013.0,\n" +
                "    \"humidity\": 56,\n" +
                "    \"sea_level\": 1013,\n" +
                "    \"grnd_level\": 1000\n" +
                "  },\n" +
                "  \"wind\": {\n" +
                "    \"speed\": 5.1\n" +
                "  },\n" +
                "  \"sys\": {\n" +
                "    \"sunrise\": 1618320997,\n" +
                "    \"sunset\": 1618364835\n" +
                "  },\n" +
                "  \"timezone\": 3600,\n" +
                "  \"name\": \"Tokyo\",\n" +
                "  \"visibility\": 10000\n" +
                "}";

        WeatherData weatherData = weatherDataParser.parse(validJson);

        assertNotNull(weatherData);
        assertEquals("Clear", weatherData.getWeather().getMain());
        assertEquals("clear sky", weatherData.getWeather().getDescription());
        assertEquals(298.72, weatherData.getTemperature().getTemp());
        assertEquals(5.1, weatherData.getWind().getSpeed());
        assertEquals(1618320997L, weatherData.getSys().getSunrise());
        assertEquals("Tokyo", weatherData.getName());
        assertEquals(10000, weatherData.getVisibility());
    }

    @Test
    void testParse_InvalidJson() {
        String invalidJson = "{\n" +
                "  \"weather\": [{\n" +
                "    \"main\": \"Clear\"\n" +
                "  }],\n" +
                "  \"main\": {\n" +
                "    \"temp\": 298.72\n" +
                "  }\n" +
                "}";

        WeatherData data = weatherDataParser.parse(invalidJson);

        assertNotNull(data.getSys());
        assertEquals(0, data.getSys().getSunset());
    }

    @Test
    void testParse_MissingFields() {
        String incompleteJson = "{\n" +
                "  \"weather\": [{\n" +
                "    \"main\": \"Clear\",\n" +
                "    \"description\": \"clear sky\"\n" +
                "  }],\n" +
                "  \"main\": {\n" +
                "    \"temp\": 298.72\n" +
                "  },\n" +
                "  \"wind\": {\n" +
                "    \"speed\": 5.1\n" +
                "  },\n" +
                "  \"timezone\": 3600,\n" +
                "  \"name\": \"Tokyo\"\n" +
                "}";

        WeatherData weatherData = weatherDataParser.parse(incompleteJson);

        assertNotNull(weatherData);
        assertEquals("Clear", weatherData.getWeather().getMain());
        assertEquals(0, weatherData.getTemperature().getFeelsLike());
        assertEquals(5.1, weatherData.getWind().getSpeed());
        assertNotNull(weatherData.getSys());
        assertEquals(0, weatherData.getSys().getSunset());
    }

    @Test
    void testParse_EmptyJson() {
        String emptyJson = "{}";

        WeatherData weatherData = weatherDataParser.parse(emptyJson);

        assertNotNull(weatherData);
        assertNotNull(weatherData.getWeather());
        assertNotNull(weatherData.getTemperature());
        assertNotNull(weatherData.getWind());
        assertNotNull(weatherData.getSys());
        assertNotNull(weatherData.getName());
        assertNull(weatherData.getWeather().getMain());
        assertEquals(0, weatherData.getVisibility());
    }
}