package com.nxhu.library.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDTO {

    private String nationality;

    private String bio;
}
