package com.rural.platform.service;

import com.rural.platform.vo.WeatherVO;
import java.util.List;
import java.util.Map;

public interface WeatherApiService {
    /**
     * 获取城市ID
     * @param location 城市名称
     * @return 城市信息列表
     */
    List<Map<String, String>> searchCity(String location);

    /**
     * 获取实时天气信息
     * @param cityName 城市名称
     * @return 天气信息
     */
    WeatherVO getWeatherInfo(String cityName);
} 