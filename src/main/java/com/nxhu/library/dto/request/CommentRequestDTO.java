package com.nxhu.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDTO {

    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}
