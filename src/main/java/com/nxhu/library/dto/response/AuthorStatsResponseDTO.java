package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorStatsResponseDTO {

    private Long authorId;
    private String authorName;
    private long totalBooks;
    private long totalDownloads;
    private long totalComments;
    private long totalFavorites;
    private Double averageRating;
}
