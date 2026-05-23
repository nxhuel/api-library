package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMetricsResponseDTO {

    private long totalBooks;
    private long totalUsers;
    private long totalAuthors;
    private long totalAdmins;
    private long totalDownloads;
    private long totalComments;
    private long totalFavorites;
    private long deletedBooks;
    private long deletedComments;
}
