package com.rural.platform.service;

import com.rural.platform.entity.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification markAsRead(Long id);
}
