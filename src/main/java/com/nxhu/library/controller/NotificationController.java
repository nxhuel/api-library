package com.nxhu.library.controller;

import com.nxhu.library.dto.response.NotificationResponseDTO;
import com.nxhu.library.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationResponseDTO> response = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadNotifications(@PathVariable Long userId) {
        List<NotificationResponseDTO> response = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<NotificationResponseDTO> createNotification(@PathVariable Long userId,
                                                                       @RequestParam String message) {
        NotificationResponseDTO response = notificationService.createNotification(userId, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
