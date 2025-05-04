package com.rural.platform.vo;

import lombok.Data;
import java.util.List;

@Data
public class WeatherVO {
    private CurrentWeather current;
    private AirQualityInfo airQuality;
    private List<WeatherIndex> indices;
    private List<WeatherWarningInfo> warnings;
    private ForecastInfo forecast;

    @Data
    public static class CurrentWeather {
        private Double temperature;
        private Double feelsLike;
        private String description;
        private String weatherType;
        private Double windSpeed;
        private Integer humidity;
        private String sunrise;
        private String sunset;
    }

    @Data
    public static class AirQualityInfo {
        private Integer aqi;
        private String level;
        private Double pm25;
        private Double pm10;
        private Double no2;
    }

    @Data
    public static class WeatherIndex {
        private String title;
        private String value;
        private String iconType;
    }

    @Data
    public static class WeatherWarningInfo {
        private Long id;
        private String type;
        private String content;
        private String time;
    }

    @Data
    public static class ForecastInfo {
        private List<String> dates;
        private List<Double> highTemps;
        private List<Double> lowTemps;
        private List<Integer> precipitation;
    }
} 