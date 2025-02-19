package me.artemiyulyanov.web.endpoints.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.artemiyulyanov.web.WebResponseParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class WeatherDataParser implements WebResponseParser<WeatherData> {
    @Override
    public WeatherData parse(String response) {
        JSONObject jsonObject = new JSONObject(response);

        WeatherData weatherData = new WeatherData();

        JSONArray weatherArray = jsonObject.optJSONArray("weather");
        JSONObject weatherJson = null;

        if (weatherArray != null) weatherJson = weatherArray.optJSONObject(0);
        WeatherData.Weather weather = new WeatherData.Weather();

        if (weatherArray != null) {
            weather.setMain(weatherJson.optString("main"));
            weather.setDescription(weatherJson.optString("description"));
        }

        weatherData.setWeather(weather);

        JSONObject tempJson = jsonObject.optJSONObject("main");
        WeatherData.Temperature temperature = new WeatherData.Temperature();

        if (tempJson != null) {
            temperature.setTemp(tempJson.optDouble("temp", 0));
            temperature.setFeelsLike(tempJson.optDouble("feels_like", 0));
            temperature.setTempMin(tempJson.optDouble("temp_min", 0));
            temperature.setTempMax(tempJson.optDouble("temp_max", 0));
            temperature.setPressure(tempJson.optDouble("pressure", 0));
            temperature.setHumidity(tempJson.optDouble("humidity", 0));
            temperature.setSeaLevel(tempJson.optDouble("sea_level", 0));
            temperature.setGrndLevel(tempJson.optDouble("grnd_level", 0));
        }

        weatherData.setTemperature(temperature);

        JSONObject windJson = jsonObject.optJSONObject("wind");
        WeatherData.Wind wind = new WeatherData.Wind();

        if (windJson != null) {
            wind.setSpeed(windJson.optDouble("speed", 0));
        }

        weatherData.setWind(wind);

        JSONObject sysJson = jsonObject.optJSONObject("sys");
        WeatherData.Sys sys = new WeatherData.Sys();

        if (sysJson != null) {
            sys.setSunrise(sysJson.optLong("sunrise", 0));
            sys.setSunset(sysJson.optLong("sunset", 0));
        }

        weatherData.setSys(sys);

        weatherData.setTimezone(jsonObject.optInt("timezone", 0));
        weatherData.setName(jsonObject.optString("name"));
        weatherData.setVisibility(jsonObject.optInt("visibility", 0));

        weatherData.setTimestamp(System.currentTimeMillis());

        return weatherData;
    }
}
