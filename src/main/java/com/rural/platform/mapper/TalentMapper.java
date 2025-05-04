package com.rural.platform.mapper;

import com.rural.platform.entity.Talent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TalentMapper {
    /**
     * 获取才艺展示列表
     * @return 才艺展示列表
     */
    List<Talent> selectTalentList();
    
    /**
     * 根据ID查询才艺展示
     * @param id 才艺ID
     * @return 才艺信息
     */
    Talent selectTalentById(@Param("id") Long id);
    
    /**
     * 更新才艺信息
     * @param talent 才艺信息
     * @return 影响行数
     */
    int updateTalent(Talent talent);
} 