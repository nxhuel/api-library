package com.nxhu.library.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO {

    private Long userId;
    private String userName;
    private String userEmail;
    private String nationality;
    private String bio;
    private Integer starCounts;
}
