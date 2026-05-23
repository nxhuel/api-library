package com.nxhu.library.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletedCommentResponseDTO {

    private Long id;
    private String content;
    private String userName;
    private String bookTitle;
    private LocalDateTime createdAt;
}
