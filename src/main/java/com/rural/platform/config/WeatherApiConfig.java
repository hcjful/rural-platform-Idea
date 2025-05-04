package com.rural.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiConfig {
    private String key;
    private String baseUrl = "https://devapi.qweather.com/v7";
} 