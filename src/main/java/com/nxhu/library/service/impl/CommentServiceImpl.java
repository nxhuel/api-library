package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.CommentRequestDTO;
import com.nxhu.library.dto.response.CommentResponseDTO;
import com.nxhu.library.persistence.entity.CommentEntity;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.CommentRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public CommentResponseDTO createComment(Long bookId, CommentRequestDTO request) {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        CommentEntity entity = CommentEntity.builder()
                .content(request.getContent())
                .user(user)
                .book(book)
                .build();

        CommentEntity saved = commentRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByBook(Long bookId) {
        return commentRepository.findByBookIdOrderByCreatedAtDesc(bookId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO request) {
        CommentEntity entity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        entity.setContent(request.getContent());
        CommentEntity saved = commentRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    private CommentResponseDTO toResponse(CommentEntity entity) {
        return CommentResponseDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .bookId(entity.getBook().getId())
                .bookTitle(entity.getBook().getTitle())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
