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
## Endpoints
This SDK has a single endpoint ```WeatherSdk::getWeather``` that returns ```WeatherData``` object with the further data:
[WeatherData.java](src/main/java/me/artemiyulyanov/web/endpoints/weather/WeatherData.java)

## Modes
This SDK is supposed to work in two of available modes: ```WeatherSdkMode.ON_DEMAND``` and ```WeatherSdkMode.POLLING```
- **ON_DEMAND**: The SDK fetches to the API every time the user refers to an endpoint
- **POLLING**: The SDK keeps a cache data that updates every 10 minutes and referring to an endpoint processes only when it is necessary (when the data gets unfreshed)
