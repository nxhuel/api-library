package com.nxhu.library.dto.response;

import com.nxhu.library.persistence.entity.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;
    private Long userId;
    private String email;
    private String name;
    private Role role;
    private String photo;
}
