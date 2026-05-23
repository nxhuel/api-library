package com.nxhu.library.dto.response;

import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.entity.enums.UserStatus;
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
    private String photo;
    private UserStatus status;
    private LocalDateTime createdAt;
}
