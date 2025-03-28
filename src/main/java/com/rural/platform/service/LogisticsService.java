package com.rural.platform.service;

import com.rural.platform.entity.Logistics;
import com.rural.platform.entity.LogisticsTrack;

import java.util.List;

public interface LogisticsService {
    Logistics createLogistics(Long orderId, String carrier);
    Logistics getLogisticsById(Long id);
    Logistics getLogisticsByOrderId(Long orderId);
    List<LogisticsTrack> getLogisticsTracks(Long logisticsId);
    void updateLogisticsStatus(Long id, String status, String location);
    void addLogisticsTrack(Long logisticsId, String location, String description);
}

