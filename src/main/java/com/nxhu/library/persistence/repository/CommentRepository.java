package com.nxhu.library.persistence.repository;

import com.nxhu.library.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBookIdOrderByCreatedAtDesc(Long bookId);

    List<CommentEntity> findByUserId(Long userId);

    long countByDeletedTrue();

    long countByBookId(Long bookId);
}
