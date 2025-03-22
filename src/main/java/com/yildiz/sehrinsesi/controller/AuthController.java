package com.yildiz.sehrinsesi.controller;

import com.yildiz.sehrinsesi.dto.*;
import com.yildiz.sehrinsesi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO dto) {
        String message = authService.registerUser(dto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto) {
        String token = authService.loginUser(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        return ResponseEntity.ok(authService.getAuthenticatedUser());
    }
}