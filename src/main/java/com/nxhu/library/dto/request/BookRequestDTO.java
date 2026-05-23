package com.nxhu.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank
    private String title;

    private String description;

    private String gender;

    private Integer numPages;

    private String coverImage;

    @NotNull
    private Long authorId;

    @NotNull
    private Long uploadedById;
}
