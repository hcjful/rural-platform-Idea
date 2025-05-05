package com.rural.platform.service.impl;

import com.rural.platform.entity.Talent;
import com.rural.platform.mapper.TalentMapper;
import com.rural.platform.service.TalentService;
import com.rural.platform.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalentServiceImpl implements TalentService {

    @Autowired
    private TalentMapper talentMapper;

    @Override
    public Page getAllTalents(Integer pageNum, Integer pageSize, String category, String search) {
        // 获取总记录数
        int totalNum = talentMapper.countTalents(category, search);
        
        // 创建分页对象
        Page page = new Page(pageNum, pageSize, totalNum, null);
        
        // 获取当前页数据
        List<Talent> records = talentMapper.findByPage(page.getLimitIndex(), pageSize, category, search);
        
        // 设置结果列表
        page.setResultList(records);
        
        return page;
    }

    @Override
    public List<Talent> getAllTalents() {
        return talentMapper.selectTalentList();
    }

    @Override
    public Talent likeTalent(Long id) {
        Talent talent = talentMapper.selectTalentById(id);
        if (talent == null) {
            throw new RuntimeException("Talent not found");
        }
        talent.setIsLiked(!talent.getIsLiked());
        talent.setLikes(talent.getIsLiked() ? talent.getLikes() + 1 : talent.getLikes() - 1);
        talentMapper.updateTalent(talent);
        return talent;
    }
}
