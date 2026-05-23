package com.nxhu.library.persistence.repository;

import com.nxhu.library.persistence.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findByUserId(Long userId);

    Optional<FavoriteEntity> findByUserIdAndBookId(Long userId, Long bookId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    long countByBookId(Long bookId);
}
