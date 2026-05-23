package com.nxhu.library.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;
}
