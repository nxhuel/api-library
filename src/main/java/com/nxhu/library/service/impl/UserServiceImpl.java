package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.UserRequestDTO;
import com.nxhu.library.dto.request.UserRoleRequestDTO;
import com.nxhu.library.dto.request.UserStatusRequestDTO;
import com.nxhu.library.dto.response.UserResponseDTO;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        log.info("createUser email={}", request.getEmail());
        UserEntity entity = UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .role(request.getRole())
                .photo(request.getPhoto())
                .build();
        UserEntity saved = userRepository.save(entity);
        log.info("user_created id={}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        entity.setEmail(request.getEmail());
        entity.setName(request.getName());
        entity.setPassword(request.getPassword());
        entity.setRole(request.getRole());
        entity.setPhoto(request.getPhoto());
        UserEntity saved = userRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserStatus(Long id, UserStatusRequestDTO request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        entity.setStatus(request.getStatus());
        UserEntity saved = userRepository.save(entity);
        log.info("user_status_updated id={} status={}", id, request.getStatus());
        return toResponse(saved);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserRole(Long id, UserRoleRequestDTO request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        entity.setRole(request.getRole());
        UserEntity saved = userRepository.save(entity);
        log.info("user_role_updated id={} role={}", id, request.getRole());
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO toResponse(UserEntity entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .role(entity.getRole())
                .photo(entity.getPhoto())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
