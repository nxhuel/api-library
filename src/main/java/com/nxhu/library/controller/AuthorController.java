package com.nxhu.library.controller;

import com.nxhu.library.dto.request.AuthorRequestDTO;
import com.nxhu.library.dto.response.AuthorResponseDTO;
import com.nxhu.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<AuthorResponseDTO> createAuthor(@PathVariable Long userId,
                                                           @Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO response = authorService.createAuthor(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long userId) {
        AuthorResponseDTO response = authorService.getAuthorById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        List<AuthorResponseDTO> response = authorService.getAllAuthors();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Long userId,
                                                           @Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO response = authorService.updateAuthor(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long userId) {
        authorService.deleteAuthor(userId);
        return ResponseEntity.noContent().build();
    }
}
