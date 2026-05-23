package com.nxhu.library.controller;

import com.nxhu.library.dto.request.UserRequestDTO;
import com.nxhu.library.dto.request.UserRoleRequestDTO;
import com.nxhu.library.dto.request.UserStatusRequestDTO;
import com.nxhu.library.dto.response.UserResponseDTO;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable Role role) {
        List<UserResponseDTO> response = userService.getUsersByRole(role);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                       @Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDTO> updateUserStatus(@PathVariable Long id,
                                                             @Valid @RequestBody UserStatusRequestDTO request) {
        UserResponseDTO response = userService.updateUserStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(@PathVariable Long id,
                                                           @Valid @RequestBody UserRoleRequestDTO request) {
        UserResponseDTO response = userService.updateUserRole(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
