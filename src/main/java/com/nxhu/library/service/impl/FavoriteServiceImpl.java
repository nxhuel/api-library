package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.FavoriteRequestDTO;
import com.nxhu.library.dto.response.FavoriteResponseDTO;
import com.nxhu.library.persistence.entity.FavoriteEntity;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.FavoriteRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.FavoriteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public FavoriteResponseDTO addFavorite(FavoriteRequestDTO request) {
        if (favoriteRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())) {
            throw new IllegalStateException("Book already favorited");
        }

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        FavoriteEntity entity = FavoriteEntity.builder()
                .user(user).book(book).build();

        FavoriteEntity saved = favoriteRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long bookId) {
        FavoriteEntity entity = favoriteRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found"));
        favoriteRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteResponseDTO> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorited(Long userId, Long bookId) {
        return favoriteRepository.existsByUserIdAndBookId(userId, bookId);
    }

    private FavoriteResponseDTO toResponse(FavoriteEntity entity) {
        return FavoriteResponseDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .bookId(entity.getBook().getId())
                .bookTitle(entity.getBook().getTitle())
                .build();
    }
}
