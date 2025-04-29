package com.rural.platform.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    private String code;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("fxLink")
    private String fxLink;
    private NowWeather now;
    private Refer refer;

    @Data
    public static class NowWeather {
        @JsonProperty("obsTime")
        private String obsTime;
        private String temp;
        @JsonProperty("feelsLike")
        private String feelsLike;
        private String icon;
        private String text;
        private String wind360;
        @JsonProperty("windDir")
        private String windDir;
        @JsonProperty("windScale")
        private String windScale;
        @JsonProperty("windSpeed")
        private String windSpeed;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String dew;
    }

    @Data
    public static class Refer {
        private List<String> sources;
        private List<String> license;
    }
}