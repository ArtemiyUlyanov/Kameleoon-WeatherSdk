package me.artemiyulyanov.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.StringJoiner;

public class WebRequestProcessingService {
    public static <T> T get(String endpoint, Map<String, String> params, WebResponseParser<T> parser) throws WebSdkExpection {
        try {
            String urlString = endpoint + "?" + params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce(new StringJoiner("&"),
                            StringJoiner::add,
                            (j1, j2) -> j1);

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return parser.parse(response.toString());
        } catch (Exception e) {
            throw new WebSdkExpection("Failed to fetch weather data", e);
        }
    }
}