package com.nxhu.library.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nxhu.library.persistence.entity.BookEntity;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long> {

}
