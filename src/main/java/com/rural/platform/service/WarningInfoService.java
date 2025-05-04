package com.rural.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rural.platform.dto.WarningInfoQuery;
import com.rural.platform.entity.WarningInfo;
import com.rural.platform.mapper.WarningInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarningInfoService {
    @Autowired
    private WarningInfoMapper warningInfoMapper;
    


    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("pending", warningInfoMapper.countByStatus("pending"));
        stats.put("processing", warningInfoMapper.countByStatus("processing"));
        stats.put("resolved", warningInfoMapper.countByStatus("resolved"));
        stats.put("total", warningInfoMapper.selectCount(null));
        return stats;
    }

    public Page<WarningInfo> getWarningInfoList(WarningInfoQuery query) {
        Page<WarningInfo> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        List<WarningInfo> records = warningInfoMapper.getWarningInfoWithRecords(
            page,
            query.getKeyword(),
            query.getType(),
            query.getStatus()
        );
        page.setRecords(records);
        return page;
    }

    public void createWarningInfo(WarningInfo warningInfo) {
        warningInfo.setCreateTime(LocalDateTime.now());
        warningInfo.setCreateDate(LocalDateTime.now());
        warningInfo.setDelFlag("0");
        warningInfoMapper.insert(warningInfo);
    }

    public void updateStatus(Long id, String status) {
        WarningInfo warningInfo = new WarningInfo();
        warningInfo.setId(id);
        warningInfo.setStatus(status);
        warningInfo.setUpdateDate(LocalDateTime.now());
        warningInfoMapper.updateById(warningInfo);
    }
}