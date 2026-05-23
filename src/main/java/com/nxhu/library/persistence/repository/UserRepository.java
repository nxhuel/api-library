package com.nxhu.library.persistence.repository;

import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findByRole(Role role);

    long countByRole(Role role);
}
