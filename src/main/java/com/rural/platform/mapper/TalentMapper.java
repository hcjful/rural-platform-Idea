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

    /**
     * 统计符合条件的记录总数
     * @param category 分类
     * @param search 搜索关键词
     * @return 记录总数
     */
    int countTalents(@Param("category") String category, @Param("search") String search);

    /**
     * 分页查询才艺展示列表
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @param category 分类
     * @param search 搜索关键词
     * @return 才艺展示列表
     */
    List<Talent> findByPage(@Param("offset") Integer offset, 
                           @Param("pageSize") Integer pageSize,
                           @Param("category") String category,
                           @Param("search") String search);
} 