package com.nxhu.library.controller;

import com.nxhu.library.dto.request.LoginRequestDTO;
import com.nxhu.library.dto.request.UserRequestDTO;
import com.nxhu.library.dto.response.AuthResponseDTO;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.repository.UserRepository;
import com.nxhu.library.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(
                new User(user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))));

        return ResponseEntity.ok(toAuthResponse(token, user));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        UserEntity entity = UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .photo(request.getPhoto())
                .build();

        UserEntity saved = userRepository.save(entity);

        String token = jwtUtil.generateToken(
                new User(saved.getEmail(), saved.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + saved.getRole().name()))));

        return ResponseEntity.status(HttpStatus.CREATED).body(toAuthResponse(token, saved));
    }

    private AuthResponseDTO toAuthResponse(String token, UserEntity user) {
        return AuthResponseDTO.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .photo(user.getPhoto())
                .build();
    }
}
