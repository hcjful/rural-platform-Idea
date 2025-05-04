package com.rural.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rural.platform.entity.WarningInfo;
import com.rural.platform.dto.WarningInfoQuery;
import com.rural.platform.service.WarningInfoService;
import com.rural.platform.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/warning")
public class WarningInfoController {
    @Autowired
    private WarningInfoService warningInfoService;

    @GetMapping("/statistics")
    public R getStatistics() {
        return R.ok().data(warningInfoService.getStatistics());
    }

    @GetMapping("/list")
    public R getList(WarningInfoQuery query) {
        Page<WarningInfo> page = warningInfoService.getWarningInfoList(query);
        return R.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    @PostMapping
    public R create(@RequestBody WarningInfo warningInfo) {
        warningInfoService.createWarningInfo(warningInfo);
        return R.ok();
    }

    @PutMapping("/status/{id}")
    public R updateStatus(@PathVariable Long id, @RequestParam String status) {
        warningInfoService.updateStatus(id, status);
        return R.ok();
    }
}
