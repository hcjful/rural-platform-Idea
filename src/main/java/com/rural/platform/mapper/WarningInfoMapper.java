package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rural.platform.entity.WarningInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface WarningInfoMapper extends BaseMapper<WarningInfo> {
    Integer countByStatus(@Param("status") String status);
    
    List<WarningInfo> getWarningInfoWithRecords(Page<WarningInfo> page, 
                                               @Param("keyword") String keyword,
                                               @Param("type") String type,
                                               @Param("status") String status);
}
