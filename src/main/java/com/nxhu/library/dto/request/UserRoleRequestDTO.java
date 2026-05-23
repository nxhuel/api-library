package com.nxhu.library.dto.request;

import com.nxhu.library.persistence.entity.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleRequestDTO {

    @NotNull
    private Role role;
}
