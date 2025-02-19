# Kameleoon

## Introduction
Kameleoon Java WeatherSDK is an estimated introduction task for Kameloon (An Europe-based company specified in A/B testing and personalization) to implement easily accessing to Openweathermap API

## Usage Example
This is an example of executing a request to Openweathermap API using WeatherSDK

```java
import me.artemiyulyanov.web.endpoints.weather.WeatherData;

public class WeatherSdkApplication {
    public static void main(String[] args) throws Exception {
        WeatherSdk sdk = new WeatherSdk("YOUR_ACCESS_TOKEN", WeatherSdkMode.POLLING); // Initialization of the SDK instance

        WeatherData data = sdk.getWeather("Tokyo"); // Fetching to getting weather endpoint to the API
        System.out.println(data.getWeather().getMain()); // Printing the weather
    }
}
```
