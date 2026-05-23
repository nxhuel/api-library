package com.nxhu.library.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long id;
    private String message;
    private Boolean read;
    private LocalDateTime createdAt;
}
