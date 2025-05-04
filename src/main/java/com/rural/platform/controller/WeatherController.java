package com.rural.platform.controller;

import com.rural.platform.response.WeatherResponse;
import com.rural.platform.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/now")
    public ResponseEntity<WeatherResponse> getCurrentWeather(@RequestParam String city) {
        WeatherResponse data = weatherService.getWeather(city);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<WeatherResponse> getWeatherByPath(@PathVariable String cityName) {
        WeatherResponse data = weatherService.getWeather(cityName);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<Map<String, String>>> searchCities(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(weatherService.searchCity(query));
    }
} 