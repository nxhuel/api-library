package com.nxhu.library.controller;

import com.nxhu.library.dto.request.FavoriteRequestDTO;
import com.nxhu.library.dto.response.FavoriteResponseDTO;
import com.nxhu.library.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/api/favorites")
    public ResponseEntity<FavoriteResponseDTO> addFavorite(@Valid @RequestBody FavoriteRequestDTO request) {
        FavoriteResponseDTO response = favoriteService.addFavorite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/api/favorites/user/{userId}/book/{bookId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long bookId) {
        favoriteService.removeFavorite(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/users/{userId}/favorites")
    public ResponseEntity<List<FavoriteResponseDTO>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoriteResponseDTO> response = favoriteService.getFavoritesByUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users/{userId}/favorites/{bookId}")
    public ResponseEntity<Boolean> isFavorited(@PathVariable Long userId, @PathVariable Long bookId) {
        boolean favorited = favoriteService.isFavorited(userId, bookId);
        return ResponseEntity.ok(favorited);
    }
}
