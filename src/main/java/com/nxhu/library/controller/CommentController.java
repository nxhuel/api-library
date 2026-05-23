package com.nxhu.library.controller;

import com.nxhu.library.dto.request.CommentRequestDTO;
import com.nxhu.library.dto.response.CommentResponseDTO;
import com.nxhu.library.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long bookId,
                                                             @Valid @RequestBody CommentRequestDTO request) {
        CommentResponseDTO response = commentService.createComment(bookId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/books/{bookId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByBook(@PathVariable Long bookId) {
        List<CommentResponseDTO> response = commentService.getCommentsByBook(bookId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id,
                                                             @Valid @RequestBody CommentRequestDTO request) {
        CommentResponseDTO response = commentService.updateComment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
