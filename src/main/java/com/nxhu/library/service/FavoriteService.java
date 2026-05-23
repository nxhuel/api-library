package com.nxhu.library.service;

import com.nxhu.library.dto.request.FavoriteRequestDTO;
import com.nxhu.library.dto.response.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteResponseDTO addFavorite(FavoriteRequestDTO request);

    void removeFavorite(Long userId, Long bookId);

    List<FavoriteResponseDTO> getFavoritesByUser(Long userId);

    boolean isFavorited(Long userId, Long bookId);
}
