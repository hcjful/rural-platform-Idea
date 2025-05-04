package com.rural.platform.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.rural.platform.config.WeatherApiConfig;
import com.rural.platform.service.WeatherApiService;
import com.rural.platform.vo.WeatherVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiServiceImpl implements WeatherApiService {

    private final WeatherApiConfig config;
    private final RestTemplate restTemplate;

    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.version}")
    private String apiVersion;
    
    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Override
    public List<Map<String, String>> searchCity(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/v" + apiVersion + "/city/lookup")
                .queryParam("key", apiKey)
                .queryParam("location", query)
                .build()
                .toUriString();

        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            List<Map<String, String>> results = new ArrayList<>();
            
            if (response != null && response.has("location")) {
                for (JsonNode location : response.get("location")) {
                    Map<String, String> city = new HashMap<>();
                    city.put("value", location.get("name").asText());
                    city.put("label", String.format("%s, %s", 
                            location.get("name").asText(),
                            location.get("adm1").asText()));
                    results.add(city);
                }
            }
            
            return results;
        } catch (Exception e) {
            log.error("搜索城市失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public WeatherVO getWeatherInfo(String cityName) {
        try {
            // 1. 先获取城市ID
            List<Map<String, String>> cities = searchCity(cityName);
            if (cities.isEmpty()) {
                log.error("未找到城市: {}", cityName);
                return null;
            }
            String location = cities.get(0).get("value");

            WeatherVO weatherVO = new WeatherVO();

            // 2. 获取实时天气
            String weatherUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("/v" + apiVersion + "/weather/now")
                    .queryParam("key", apiKey)
                    .queryParam("location", location)
                    .build()
                    .toUriString();

            JsonNode weatherResponse = restTemplate.getForObject(weatherUrl, JsonNode.class);
            if (weatherResponse != null && weatherResponse.has("now")) {
                JsonNode now = weatherResponse.get("now");
                WeatherVO.CurrentWeather current = new WeatherVO.CurrentWeather();
                current.setTemperature(Double.parseDouble(now.get("temp").asText()));
                current.setFeelsLike(Double.parseDouble(now.get("feelsLike").asText()));
                current.setDescription(now.get("text").asText());
                current.setWeatherType(mapWeatherType(now.get("text").asText()));
                current.setWindSpeed(Double.parseDouble(now.get("windSpeed").asText()));
                current.setHumidity(Integer.parseInt(now.get("humidity").asText()));
                
                // 获取日出日落时间
                String sunUrl = UriComponentsBuilder
                    .fromHttpUrl(config.getBaseUrl() + "/astronomy/sun")
                    .queryParam("location", location)
                    .queryParam("key", config.getKey())
                    .build()
                    .toUriString();
                
                JsonNode sunResponse = restTemplate.getForObject(sunUrl, JsonNode.class);
                if (sunResponse != null && sunResponse.has("sunrise") && sunResponse.has("sunset")) {
                    current.setSunrise(formatTime(sunResponse.get("sunrise").asText()));
                    current.setSunset(formatTime(sunResponse.get("sunset").asText()));
                }
                
                weatherVO.setCurrent(current);
            }

            // 3. 获取空气质量
            String airUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("/v" + apiVersion + "/air/now")
                    .queryParam("key", apiKey)
                    .queryParam("location", location)
                    .build()
                    .toUriString();

            JsonNode airResponse = restTemplate.getForObject(airUrl, JsonNode.class);
            if (airResponse != null && airResponse.has("now")) {
                JsonNode air = airResponse.get("now");
                WeatherVO.AirQualityInfo airQuality = new WeatherVO.AirQualityInfo();
                airQuality.setAqi(Integer.parseInt(air.get("aqi").asText()));
                airQuality.setLevel(getAirQualityLevel(Integer.parseInt(air.get("aqi").asText())));
                airQuality.setPm25(Double.parseDouble(air.get("pm2p5").asText()));
                airQuality.setPm10(Double.parseDouble(air.get("pm10").asText()));
                airQuality.setNo2(Double.parseDouble(air.get("no2").asText()));
                weatherVO.setAirQuality(airQuality);
            }

            // 4. 获取天气预报
            String forecastUrl = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrl() + "/weather/7d")
                .queryParam("location", location)
                .queryParam("key", config.getKey())
                .build()
                .toUriString();

            JsonNode forecastResponse = restTemplate.getForObject(forecastUrl, JsonNode.class);
            if (forecastResponse != null && forecastResponse.has("daily")) {
                WeatherVO.ForecastInfo forecast = new WeatherVO.ForecastInfo();
                List<String> dates = new ArrayList<>();
                List<Double> highTemps = new ArrayList<>();
                List<Double> lowTemps = new ArrayList<>();
                List<Integer> precipitation = new ArrayList<>();

                for (JsonNode day : forecastResponse.get("daily")) {
                    dates.add(day.get("fxDate").asText());
                    highTemps.add(Double.parseDouble(day.get("tempMax").asText()));
                    lowTemps.add(Double.parseDouble(day.get("tempMin").asText()));
                    precipitation.add(Integer.parseInt(day.get("precip").asText().split("\\.")[0]));
                }

                forecast.setDates(dates);
                forecast.setHighTemps(highTemps);
                forecast.setLowTemps(lowTemps);
                forecast.setPrecipitation(precipitation);
                weatherVO.setForecast(forecast);
            }

            // 5. 获取天气预警
            String warningUrl = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrl() + "/warning/now")
                .queryParam("location", location)
                .queryParam("key", config.getKey())
                .build()
                .toUriString();

            JsonNode warningResponse = restTemplate.getForObject(warningUrl, JsonNode.class);
            if (warningResponse != null && warningResponse.has("warning")) {
                List<WeatherVO.WeatherWarningInfo> warnings = new ArrayList<>();
                for (JsonNode warning : warningResponse.get("warning")) {
                    WeatherVO.WeatherWarningInfo warningInfo = new WeatherVO.WeatherWarningInfo();
                    warningInfo.setId(warning.get("id").asLong());
                    warningInfo.setType(warning.get("type").asText());
                    warningInfo.setContent(warning.get("text").asText());
                    warningInfo.setTime(warning.get("pubTime").asText());
                    warnings.add(warningInfo);
                }
                weatherVO.setWarnings(warnings);
            }

            // 6. 设置天气指数
            List<WeatherVO.WeatherIndex> indices = new ArrayList<>();
            String indicesUrl = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrl() + "/indices/1d")
                .queryParam("location", location)
                .queryParam("key", config.getKey())
                .queryParam("type", "1,2,3,4")  // UV指数、穿衣指数、空气污染扩散条件指数、感冒指数
                .build()
                .toUriString();

            JsonNode indicesResponse = restTemplate.getForObject(indicesUrl, JsonNode.class);
            if (indicesResponse != null && indicesResponse.has("daily")) {
                for (JsonNode index : indicesResponse.get("daily")) {
                    WeatherVO.WeatherIndex weatherIndex = new WeatherVO.WeatherIndex();
                    weatherIndex.setTitle(index.get("name").asText());
                    weatherIndex.setValue(index.get("category").asText());
                    weatherIndex.setIconType(mapIndexType(index.get("type").asText()));
                    indices.add(weatherIndex);
                }
            }
            weatherVO.setIndices(indices);

            return weatherVO;
        } catch (Exception e) {
            log.error("获取天气信息失败: {}", e.getMessage());
            return null;
        }
    }

    private String mapWeatherType(String text) {
        if (text.contains("晴")) return "sunny";
        if (text.contains("多云")) return "partlyCloudy";
        if (text.contains("阴")) return "cloudy";
        if (text.contains("小雨")) return "drizzle";
        if (text.contains("中雨") || text.contains("大雨")) return "rain";
        if (text.contains("暴雨")) return "heavyRain";
        if (text.contains("雷")) return "thunder";
        return "sunny";
    }

    private String getAirQualityLevel(int aqi) {
        if (aqi <= 50) return "优";
        if (aqi <= 100) return "良";
        if (aqi <= 150) return "轻度污染";
        if (aqi <= 200) return "中度污染";
        if (aqi <= 300) return "重度污染";
        return "严重污染";
    }

    private String mapIndexType(String type) {
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("1", "uv");               // UV指数
        typeMap.put("2", "visibility");       // 穿衣指数
        typeMap.put("3", "pressure");         // 空气污染扩散条件
        typeMap.put("4", "precipitation");    // 感冒指数
        return typeMap.getOrDefault(type, "uv");
    }

    private String formatTime(String time) {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            return time;
        }
    }
} 