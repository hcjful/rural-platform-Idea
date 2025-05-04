package com.rural.platform.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rural.platform.config.WeatherApiConfig;
import com.rural.platform.response.WeatherResponse;
import com.rural.platform.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    
    private final WeatherApiConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public WeatherResponse getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("城市名称不能为空");
        }

        // 1. 先获取城市ID
        String locationId = getCityId(city);
        if (locationId == null) {
            throw new RuntimeException("未找到该城市");
        }

        // 2. 获取天气数据
        String url = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                .path("/weather/now")
                .queryParam("location", locationId)
                .queryParam("key", config.getKey())
                .build()
                .toUriString();

        try {
            log.info("获取天气数据, URL: {}", url);
            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("获取天气数据失败, 状态码: {}", response.getStatusCode());
                throw new RuntimeException("获取天气数据失败");
            }
            
            WeatherResponse weatherResponse = response.getBody();
            if (weatherResponse == null || weatherResponse.getNow() == null) {
                log.error("获取天气数据失败, 响应数据为空");
                throw new RuntimeException("获取天气数据失败");
            }

            // 检查API返回的状态码
            if (!"200".equals(weatherResponse.getCode())) {
                log.error("获取天气数据失败, API状态码: {}", weatherResponse.getCode());
                throw new RuntimeException("获取天气数据失败: " + getErrorMessage(weatherResponse.getCode()));
            }
            
            return weatherResponse;
        } catch (Exception e) {
            log.error("获取天气数据失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取天气数据失败: " + e.getMessage(), e);
        }
    }

    private String getCityId(String cityName) {
        String url = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                .path("/city/lookup")
                .queryParam("location", cityName)
                .queryParam("key", config.getKey())
                .build()
                .toUriString();

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode body = response.getBody();
            
            if (body != null && "200".equals(body.path("code").asText()) && body.has("location")) {
                JsonNode firstLocation = body.get("location").get(0);
                return firstLocation.get("id").asText();
            }
            return null;
        } catch (Exception e) {
            log.error("获取城市ID失败: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Map<String, String>> searchCity(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String url = UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                .path("/city/lookup")
                .queryParam("location", query)
                .queryParam("key", config.getKey())
                .build()
                .toUriString();

        try {
            log.info("搜索城市, URL: {}", url);
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("搜索城市失败, 状态码: {}", response.getStatusCode());
                return new ArrayList<>();
            }
            
            JsonNode jsonResponse = response.getBody();
            List<Map<String, String>> results = new ArrayList<>();
            
            if (jsonResponse != null && "200".equals(jsonResponse.path("code").asText()) && jsonResponse.has("location")) {
                for (JsonNode location : jsonResponse.get("location")) {
                    Map<String, String> city = new HashMap<>();
                    city.put("value", location.get("id").asText());  // 使用城市ID作为value
                    city.put("label", String.format("%s, %s", 
                            location.get("name").asText(),
                            location.get("adm1").asText()));
                    results.add(city);
                }
            }
            
            return results;
        } catch (Exception e) {
            log.error("搜索城市失败: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private String getErrorMessage(String code) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("204", "请求成功，但你查询的地区暂时没有你需要的数据");
        errorMessages.put("400", "请求错误，可能包含错误的请求参数或缺少必选的请求参数");
        errorMessages.put("401", "认证失败，可能使用了错误的认证方式或错误的认证信息");
        errorMessages.put("402", "超过访问次数或余额不足以支持继续访问服务");
        errorMessages.put("403", "无访问权限，可能是绑定的PackageName、BundleID、域名或IP地址不正确");
        errorMessages.put("404", "查询的数据或地区不存在");
        errorMessages.put("429", "超过限定的QPM（每分钟访问次数）");
        errorMessages.put("500", "服务器内部错误");
        return errorMessages.getOrDefault(code, "未知错误");
    }
}