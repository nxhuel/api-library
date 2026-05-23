package com.nxhu.library.service.impl;

import com.nxhu.library.dto.response.NotificationResponseDTO;
import com.nxhu.library.persistence.entity.NotificationEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.repository.NotificationRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        NotificationEntity entity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + notificationId));
        entity.setRead(true);
        notificationRepository.save(entity);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        List<NotificationEntity> unread = notificationRepository.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    @Override
    @Transactional
    public NotificationResponseDTO createNotification(Long userId, String message) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        NotificationEntity entity = NotificationEntity.builder()
                .message(message)
                .user(user)
                .build();
        NotificationEntity saved = notificationRepository.save(entity);
        log.info("notification_created userId={}", userId);
        return toResponse(saved);
    }

    private NotificationResponseDTO toResponse(NotificationEntity entity) {
        return NotificationResponseDTO.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .read(entity.getRead())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
