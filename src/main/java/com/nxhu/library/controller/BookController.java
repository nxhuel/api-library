package com.nxhu.library.controller;

import com.nxhu.library.dto.request.BookRequestDTO;
import com.nxhu.library.dto.response.BookResponseDTO;
import com.nxhu.library.dto.response.DeletedBookResponseDTO;
import com.nxhu.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO response = bookService.getBookById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> response = bookService.getAllBooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookResponseDTO> response = bookService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/uploader/{userId}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByUploader(@PathVariable Long userId) {
        List<BookResponseDTO> response = bookService.getBooksByUploader(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                       @Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO response = bookService.updateBook(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDTO> uploadPdf(@PathVariable Long id,
                                                      @RequestParam("file") MultipartFile file) {
        BookResponseDTO response = bookService.uploadPdf(id, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) {
        Resource resource = bookService.getPdfResource(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"book-%d.pdf\"".formatted(id))
                .body(resource);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<DeletedBookResponseDTO>> getDeletedBooks() {
        List<DeletedBookResponseDTO> response = bookService.getDeletedBooks();
        return ResponseEntity.ok(response);
    }
}
