package com.nxhu.library.persistence.repository;

import com.nxhu.library.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<NotificationEntity> findByUserIdAndReadFalse(Long userId);

    long countByUserIdAndReadFalse(Long userId);
}
