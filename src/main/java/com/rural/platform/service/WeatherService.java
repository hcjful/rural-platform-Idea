package com.rural.platform.service;

import com.rural.platform.response.WeatherResponse;
import java.util.List;
import java.util.Map;

public interface WeatherService {

    WeatherResponse getWeather(String city);
    List<Map<String, String>> searchCity(String query);
}