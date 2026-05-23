package com.nxhu.library.service.impl;

import com.nxhu.library.dto.response.AdminMetricsResponseDTO;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.CommentRepository;
import com.nxhu.library.persistence.repository.FavoriteRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminMetricsResponseDTO getMetrics() {
        long totalBooks = bookRepository.countByDeletedFalse();
        long totalUsers = userRepository.countByRole(Role.USER);
        long totalAuthors = userRepository.countByRole(Role.AUTHOR);
        long totalAdmins = userRepository.countByRole(Role.ADMIN);
        long totalComments = commentRepository.count();
        long totalFavorites = favoriteRepository.count();
        long totalDownloads = bookRepository.findAll().stream()
                .mapToLong(b -> b.getDownloadsCount() != null ? b.getDownloadsCount() : 0)
                .sum();
        long deletedBooks = bookRepository.findAll().stream()
                .filter(b -> Boolean.TRUE.equals(b.getDeleted()))
                .count();
        long deletedComments = commentRepository.countByDeletedTrue();

        return AdminMetricsResponseDTO.builder()
                .totalBooks(totalBooks)
                .totalUsers(totalUsers)
                .totalAuthors(totalAuthors)
                .totalAdmins(totalAdmins)
                .totalDownloads(totalDownloads)
                .totalComments(totalComments)
                .totalFavorites(totalFavorites)
                .deletedBooks(deletedBooks)
                .deletedComments(deletedComments)
                .build();
    }
}
