package com.nxhu.library.service.impl;

import com.nxhu.library.dto.request.BookRequestDTO;
import com.nxhu.library.dto.response.BookResponseDTO;
import com.nxhu.library.dto.response.DeletedBookResponseDTO;
import com.nxhu.library.persistence.entity.AuthorEntity;
import com.nxhu.library.persistence.entity.BookEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.repository.AuthorRepository;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Value("${app.upload.dir:uploads/pdfs}")
    private String uploadDir;

    @Override
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO request) {
        log.info("createBook title={}", request.getTitle());
        AuthorEntity author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + request.getAuthorId()));
        UserEntity uploader = userRepository.findById(request.getUploadedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUploadedById()));

        BookEntity entity = BookEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .gender(request.getGender())
                .numPages(request.getNumPages())
                .coverImage(request.getCoverImage())
                .author(author)
                .uploadedBy(uploader)
                .build();
        BookEntity saved = bookRepository.save(entity);
        log.info("book_created id={}", saved.getId());
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
                .filter(b -> !Boolean.TRUE.equals(b.getDeleted()))
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorUserId(authorId).stream()
                .filter(b -> !Boolean.TRUE.equals(b.getDeleted()))
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDTO> getBooksByUploader(Long userId) {
        return bookRepository.findByUploadedById(userId).stream()
                .filter(b -> !Boolean.TRUE.equals(b.getDeleted()))
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
        entity.setCoverImage(request.getCoverImage());
        entity.setAuthor(author);
        entity.setUploadedBy(uploader);
        BookEntity saved = bookRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        entity.setDeleted(true);
        bookRepository.save(entity);
        log.info("book_deleted id={}", id);
    }

    @Override
    @Transactional
    public BookResponseDTO uploadPdf(Long id, MultipartFile file) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        try {
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            entity.setPdfPath(target.toString());
            BookEntity saved = bookRepository.save(entity);
            return toResponse(saved);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload PDF for book id: " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource getPdfResource(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        if (entity.getPdfPath() == null) {
            throw new RuntimeException("No PDF uploaded for book id: " + id);
        }
        try {
            Path file = Paths.get(entity.getPdfPath());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                entity.setDownloadsCount(entity.getDownloadsCount() + 1);
                bookRepository.save(entity);
                return resource;
            }
            throw new RuntimeException("PDF file not found or not readable for book id: " + id);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading PDF for book id: " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeletedBookResponseDTO> getDeletedBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> Boolean.TRUE.equals(b.getDeleted()))
                .map(b -> DeletedBookResponseDTO.builder()
                        .id(b.getId())
                        .title(b.getTitle())
                        .description(b.getDescription())
                        .gender(b.getGender())
                        .authorName(b.getAuthor().getUser().getName())
                        .uploadedByName(b.getUploadedBy().getName())
                        .build())
                .toList();
    }

    private BookResponseDTO toResponse(BookEntity entity) {
        return BookResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .gender(entity.getGender())
                .numPages(entity.getNumPages())
                .coverImage(entity.getCoverImage())
                .pdfPath(entity.getPdfPath())
                .downloadsCount(entity.getDownloadsCount())
                .rating(entity.getRating())
                .authorId(entity.getAuthor().getUserId())
                .authorName(entity.getAuthor().getUser().getName())
                .uploadedById(entity.getUploadedBy().getId())
                .uploadedByName(entity.getUploadedBy().getName())
                .build();
    }
}
