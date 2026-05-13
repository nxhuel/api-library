package com.nxhu.library.persistence.repository;

import com.nxhu.library.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorUserId(Long authorId);

    List<BookEntity> findByUploadedById(Long userId);
}
