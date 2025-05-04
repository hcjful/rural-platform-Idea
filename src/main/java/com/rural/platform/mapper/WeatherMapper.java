package com.rural.platform.mapper;

import com.rural.platform.entity.WeatherData;
import com.rural.platform.entity.AirQuality;
import com.rural.platform.entity.WeatherWarning;
import com.rural.platform.entity.WeatherForecast;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface WeatherMapper {
    String getCityCode(String cityName);
    WeatherData getCurrentWeather(String cityCode);
    AirQuality getAirQuality(String cityCode);
    List<WeatherWarning> getWeatherWarnings(String cityCode);
    List<WeatherForecast> getWeatherForecasts(String cityCode);
    List<Map<String, String>> searchCities(String query);
} 