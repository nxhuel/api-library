package com.nxhu.library.service;

import com.nxhu.library.dto.request.BookRequestDTO;
import com.nxhu.library.dto.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO request);

    BookResponseDTO getBookById(Long id);

    List<BookResponseDTO> getAllBooks();

    List<BookResponseDTO> getBooksByAuthor(Long authorId);

    List<BookResponseDTO> getBooksByUploader(Long userId);

    BookResponseDTO updateBook(Long id, BookRequestDTO request);

    void deleteBook(Long id);
}
