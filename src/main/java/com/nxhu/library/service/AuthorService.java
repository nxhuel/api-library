package com.nxhu.library.service;

import com.nxhu.library.dto.request.AuthorRequestDTO;
import com.nxhu.library.dto.response.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {

    AuthorResponseDTO createAuthor(Long userId, AuthorRequestDTO request);

    AuthorResponseDTO getAuthorById(Long userId);

    List<AuthorResponseDTO> getAllAuthors();

    AuthorResponseDTO updateAuthor(Long userId, AuthorRequestDTO request);

    void deleteAuthor(Long userId);
}
