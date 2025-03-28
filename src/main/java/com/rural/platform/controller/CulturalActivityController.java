package com.rural.platform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rural.platform.dto.CulturalActivityDTO;
import com.rural.platform.response.Result;
import com.rural.platform.service.CulturalActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cultural-activities")
public class CulturalActivityController {

    @Resource
    private CulturalActivityService culturalActivityService;

    @GetMapping
    public Result<IPage<CulturalActivityDTO>> getActivityPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        return Result.success(culturalActivityService.getActivityPage(page, pageSize, category, search));
    }

    @GetMapping("/{id}")
    public Result<CulturalActivityDTO> getActivityById(@PathVariable Long id) {
        culturalActivityService.incrementViewCount(id);
        return Result.success(culturalActivityService.getActivityById(id));
    }

    @PostMapping
    public Result<Long> createActivity(@Validated @RequestBody CulturalActivityDTO activityDTO) {
        return Result.success(culturalActivityService.createActivity(activityDTO));
    }

    @PutMapping("/{id}")
    public Result<Void> updateActivity(
            @PathVariable Long id,
            @Validated @RequestBody CulturalActivityDTO activityDTO) {
        culturalActivityService.updateActivity(id, activityDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteActivity(@PathVariable Long id) {
        culturalActivityService.deleteActivity(id);
        return Result.success();
    }
} 