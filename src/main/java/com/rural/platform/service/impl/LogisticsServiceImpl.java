package com.rural.platform.service.impl;

import com.rural.platform.entity.Logistics;
import com.rural.platform.entity.LogisticsTrack;
import com.rural.platform.mapper.LogisticsMapper;
import com.rural.platform.mapper.LogisticsTrackMapper;
import com.rural.platform.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private LogisticsTrackMapper trackMapper;

    @Override
    @Transactional
    public Logistics createLogistics(Long orderId, String carrier) {
        Logistics logistics = new Logistics();
        logistics.setOrderId(orderId);
        logistics.setCarrier(carrier);
        logistics.setTrackingNo(generateTrackingNo(carrier));
        logistics.setStatus("PENDING");

        logisticsMapper.insert(logistics);

        // 添加初始物流记录
        addLogisticsTrack(logistics.getId(), "系统", "订单已创建，等待发货");

        return logistics;
    }

    @Override
    public Logistics getLogisticsById(Long id) {
        return logisticsMapper.findById(id);
    }

    @Override
    public Logistics getLogisticsByOrderId(Long orderId) {
        return logisticsMapper.findByOrderId(orderId);
    }

    @Override
    public List<LogisticsTrack> getLogisticsTracks(Long logisticsId) {
        return trackMapper.findByLogisticsId(logisticsId);
    }

    @Override
    @Transactional
    public void updateLogisticsStatus(Long id, String status, String location) {
        Logistics logistics = logisticsMapper.findById(id);
        if (logistics != null) {
            logistics.setStatus(status);
            logistics.setCurrentLocation(location);
            if ("DELIVERED".equals(status)) {
                logistics.setDeliveredAt(LocalDateTime.now());
            }
            logisticsMapper.updateStatus(logistics);

            // 添加物流跟踪记录
            addLogisticsTrack(id, location, getStatusDescription(status));
        }
    }

    @Override
    public void addLogisticsTrack(Long logisticsId, String location, String description) {
        LogisticsTrack track = new LogisticsTrack();
        track.setLogisticsId(logisticsId);
        track.setLocation(location);
        track.setDescription(description);
        track.setTrackTime(LocalDateTime.now());

        trackMapper.insert(track);
    }

    private String generateTrackingNo(String carrier) {
        // 生成物流单号：运输公司代码 + 时间戳 + 4位随机数
        String prefix = carrier.substring(0, 2).toUpperCase();
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        String random = String.format("%04d", (int) (Math.random() * 10000));
        return prefix + timestamp + random;
    }

    private String getStatusDescription(String status) {
        switch (status) {
            case "PENDING":
                return "等待发货";
            case "SHIPPING":
                return "运输中";
            case "DELIVERED":
                return "已签收";
            default:
                return "状态更新";
        }
    }
}
