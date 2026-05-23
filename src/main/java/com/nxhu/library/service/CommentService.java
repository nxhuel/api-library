package com.nxhu.library.service;

import com.nxhu.library.dto.request.CommentRequestDTO;
import com.nxhu.library.dto.response.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(Long bookId, CommentRequestDTO request);

    List<CommentResponseDTO> getCommentsByBook(Long bookId);

    CommentResponseDTO updateComment(Long id, CommentRequestDTO request);

    void deleteComment(Long id);
}
