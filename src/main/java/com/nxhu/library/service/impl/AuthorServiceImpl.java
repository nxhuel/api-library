package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.AuthorRequestDTO;
import com.nxhu.library.dto.response.AuthorResponseDTO;
import com.nxhu.library.persistence.entity.AuthorEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.repository.AuthorRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AuthorResponseDTO createAuthor(Long userId, AuthorRequestDTO request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        AuthorEntity entity = AuthorEntity.builder()
                .user(user)
                .nationality(request.getNationality())
                .bio(request.getBio())
                .build();

        AuthorEntity saved = authorRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDTO getAuthorById(Long userId) {
        AuthorEntity entity = authorRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with user id: " + userId));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AuthorResponseDTO updateAuthor(Long userId, AuthorRequestDTO request) {
        AuthorEntity entity = authorRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with user id: " + userId));

        entity.setNationality(request.getNationality());
        entity.setBio(request.getBio());

        AuthorEntity saved = authorRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long userId) {
        if (!authorRepository.existsById(userId)) {
            throw new EntityNotFoundException("Author not found with user id: " + userId);
        }
        authorRepository.deleteById(userId);
    }

    private AuthorResponseDTO toResponse(AuthorEntity entity) {
        return AuthorResponseDTO.builder()
                .userId(entity.getUserId())
                .userName(entity.getUser().getName())
                .userEmail(entity.getUser().getEmail())
                .nationality(entity.getNationality())
                .bio(entity.getBio())
                .build();
    }
}
