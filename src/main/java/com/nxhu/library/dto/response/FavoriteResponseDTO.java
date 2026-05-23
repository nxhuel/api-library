package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDTO {

    private Long id;
    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
}
