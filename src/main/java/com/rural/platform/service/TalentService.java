package com.rural.platform.service;

import com.rural.platform.entity.Talent;
import java.util.List;

public interface TalentService {
    List<Talent> getAllTalents();
    Talent likeTalent(Long id);
}
