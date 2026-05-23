package com.nxhu.library.service;

import com.nxhu.library.dto.response.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDTO> getUserNotifications(Long userId);

    List<NotificationResponseDTO> getUnreadNotifications(Long userId);

    long getUnreadCount(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    NotificationResponseDTO createNotification(Long userId, String message);
}
