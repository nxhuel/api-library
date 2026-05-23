package com.nxhu.library.service;

import com.nxhu.library.dto.request.UserRequestDTO;
import com.nxhu.library.dto.request.UserRoleRequestDTO;
import com.nxhu.library.dto.request.UserStatusRequestDTO;
import com.nxhu.library.dto.response.UserResponseDTO;
import com.nxhu.library.persistence.entity.enums.Role;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> getUsersByRole(Role role);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    UserResponseDTO updateUserStatus(Long id, UserStatusRequestDTO request);

    UserResponseDTO updateUserRole(Long id, UserRoleRequestDTO request);

    void deleteUser(Long id);
}
