package com.rural.platform.controller;

import com.rural.platform.entity.Talent;
import com.rural.platform.service.TalentService;
import com.rural.platform.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/talents")
public class TalentController {

    @Autowired
    private TalentService talentService;

    @GetMapping
    public Result<Map<String, Object>> getAllTalents() {
        List<Talent> talents = talentService.getAllTalents();
        Map<String, Object> data = new HashMap<>();
        data.put("list", talents);
        data.put("total", talents.size());
        return Result.success(data);
    }

    @PostMapping("/{id}/like")
    public Result<Talent> likeTalent(@PathVariable Long id) {
        return Result.success(talentService.likeTalent(id));
    }
}
