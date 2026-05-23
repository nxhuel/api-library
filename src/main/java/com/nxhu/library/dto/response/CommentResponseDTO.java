package com.nxhu.library.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime createdAt;
}
