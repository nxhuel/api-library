package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletedBookResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String gender;
    private String authorName;
    private String uploadedByName;
}
