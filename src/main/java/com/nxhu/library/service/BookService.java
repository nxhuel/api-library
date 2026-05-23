package com.nxhu.library.service;

import com.nxhu.library.dto.request.BookRequestDTO;
import com.nxhu.library.dto.response.BookResponseDTO;
import com.nxhu.library.dto.response.DeletedBookResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO request);

    BookResponseDTO getBookById(Long id);

    List<BookResponseDTO> getAllBooks();

    List<BookResponseDTO> getBooksByAuthor(Long authorId);

    List<BookResponseDTO> getBooksByUploader(Long userId);

    BookResponseDTO updateBook(Long id, BookRequestDTO request);

    void deleteBook(Long id);

    BookResponseDTO uploadPdf(Long id, MultipartFile file);

    Resource getPdfResource(Long id);

    List<DeletedBookResponseDTO> getDeletedBooks();
}
