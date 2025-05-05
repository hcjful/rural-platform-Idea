package com.rural.platform.service;

import com.rural.platform.entity.Talent;
import com.rural.platform.page.Page;
import java.util.List;

public interface TalentService {
    Page getAllTalents(Integer pageNum, Integer pageSize, String category, String search);
    List<Talent> getAllTalents();
    Talent likeTalent(Long id);
}
