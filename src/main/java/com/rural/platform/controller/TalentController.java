package com.rural.platform.controller;

import com.rural.platform.entity.Talent;
import com.rural.platform.service.TalentService;
import com.rural.platform.response.Result;
import com.rural.platform.page.Page;
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
    public Result<Map<String, Object>> getAllTalents(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "12") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        Page page = talentService.getAllTalents(pageNum, pageSize, category, search);
        Map<String, Object> data = new HashMap<>();
        data.put("resultList", page.getResultList());
        data.put("totalNum", page.getTotalNum());
        data.put("pageNum", page.getPageNum());
        data.put("pageSize", page.getPageSize());
        return Result.success(data);
    }

    @PostMapping("/{id}/like")
    public Result<Talent> likeTalent(@PathVariable Long id) {
        return Result.success(talentService.likeTalent(id));
    }
}
