package com.nxhu.library.dto.response;

import com.nxhu.library.persistence.entity.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String email;
    private String name;
    private Role role;
    private LocalDateTime createdAt;
}
