package com.nxhu.library.dto.request;

import com.nxhu.library.persistence.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatusRequestDTO {

    @NotNull
    private UserStatus status;
}
