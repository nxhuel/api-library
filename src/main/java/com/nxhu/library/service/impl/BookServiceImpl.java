package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.BookRequestDTO;
import com.nxhu.library.dto.response.BookResponseDTO;
import com.nxhu.library.persistence.entity.AuthorEntity;
import com.nxhu.library.persistence.entity.BookEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.repository.AuthorRepository;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO request) {
        AuthorEntity author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + request.getAuthorId()));

        UserEntity uploader = userRepository.findById(request.getUploadedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUploadedById()));

        BookEntity entity = BookEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .gender(request.getGender())
                .numPages(request.getNumPages())
                .author(author)
                .uploadedBy(uploader)
                .build();

        BookEntity saved = bookRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorUserId(authorId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getBooksByUploader(Long userId) {
        return bookRepository.findByUploadedById(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        AuthorEntity author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + request.getAuthorId()));

        UserEntity uploader = userRepository.findById(request.getUploadedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUploadedById()));

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setGender(request.getGender());
        entity.setNumPages(request.getNumPages());
        entity.setAuthor(author);
        entity.setUploadedBy(uploader);

        BookEntity saved = bookRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookResponseDTO toResponse(BookEntity entity) {
        return BookResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .gender(entity.getGender())
                .numPages(entity.getNumPages())
                .authorId(entity.getAuthor().getUserId())
                .authorName(entity.getAuthor().getUser().getName())
                .uploadedById(entity.getUploadedBy().getId())
                .uploadedByName(entity.getUploadedBy().getName())
                .build();
    }
}
