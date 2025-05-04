package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.UserDevice;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserDeviceMapper extends BaseMapper<UserDevice> {
    List<UserDevice> selectByUserId(@Param("userId") Long userId);
    int deleteById(Long id);
    int updateCurrentDevice(Long userId, Long deviceId);
}
