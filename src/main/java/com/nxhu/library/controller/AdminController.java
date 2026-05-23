package com.nxhu.library.controller;

import com.nxhu.library.dto.response.AdminMetricsResponseDTO;
import com.nxhu.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/metrics")
    public ResponseEntity<AdminMetricsResponseDTO> getMetrics() {
        AdminMetricsResponseDTO response = adminService.getMetrics();
        return ResponseEntity.ok(response);
    }
}
