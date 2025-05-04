package com.rural.platform.service.impl;

import com.rural.platform.entity.Talent;
import com.rural.platform.mapper.TalentMapper;
import com.rural.platform.service.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalentServiceImpl implements TalentService {

    @Autowired
    private TalentMapper talentMapper;

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
