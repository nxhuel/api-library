package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.AuthorRequestDTO;
import com.nxhu.library.dto.response.AuthorResponseDTO;
import com.nxhu.library.dto.response.AuthorStatsResponseDTO;
import com.nxhu.library.persistence.entity.AuthorEntity;
import com.nxhu.library.persistence.entity.BookEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.repository.AuthorRepository;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.CommentRepository;
import com.nxhu.library.persistence.repository.FavoriteRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public AuthorResponseDTO createAuthor(Long userId, AuthorRequestDTO request) {
        log.info("createAuthor userId={}", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        AuthorEntity entity = AuthorEntity.builder()
                .user(user)
                .nationality(request.getNationality())
                .bio(request.getBio())
                .build();
        AuthorEntity saved = authorRepository.save(entity);
        log.info("author_created userId={}", saved.getUserId());
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

    @Override
    @Transactional(readOnly = true)
    public AuthorStatsResponseDTO getAuthorStats(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with user id: " + authorId));

        List<BookEntity> books = bookRepository.findByAuthorUserId(authorId);
        long totalBooks = books.size();
        long totalDownloads = books.stream().mapToLong(b -> b.getDownloadsCount() != null ? b.getDownloadsCount() : 0).sum();
        long totalComments = books.stream().mapToLong(b -> commentRepository.countByBookId(b.getId())).sum();
        long totalFavorites = books.stream().mapToLong(b -> favoriteRepository.countByBookId(b.getId())).sum();
        double averageRating = books.stream()
                .filter(b -> b.getRating() != null)
                .mapToDouble(BookEntity::getRating)
                .average()
                .orElse(0.0);

        return AuthorStatsResponseDTO.builder()
                .authorId(authorId)
                .authorName(author.getUser().getName())
                .totalBooks(totalBooks)
                .totalDownloads(totalDownloads)
                .totalComments(totalComments)
                .totalFavorites(totalFavorites)
                .averageRating(averageRating)
                .build();
    }

    private AuthorResponseDTO toResponse(AuthorEntity entity) {
        return AuthorResponseDTO.builder()
                .userId(entity.getUserId())
                .userName(entity.getUser().getName())
                .userEmail(entity.getUser().getEmail())
                .nationality(entity.getNationality())
                .bio(entity.getBio())
                .starCounts(entity.getStarCounts())
                .build();
    }
}
