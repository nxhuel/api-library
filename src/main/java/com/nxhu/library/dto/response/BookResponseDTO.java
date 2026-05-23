package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String gender;
    private Integer numPages;
    private String coverImage;
    private String pdfPath;
    private Integer downloadsCount;
    private Double rating;
    private Long authorId;
    private String authorName;
    private Long uploadedById;
    private String uploadedByName;
}
