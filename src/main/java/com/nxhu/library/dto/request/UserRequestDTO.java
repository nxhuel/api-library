package com.nxhu.library.dto.request;

import com.nxhu.library.persistence.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    private String photo;
}
