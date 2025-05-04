package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.WarningRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface WarningRecordMapper extends BaseMapper<WarningRecord> {
    List<WarningRecord> getRecordsByWarningId(@Param("warningId") Long warningId);
}
