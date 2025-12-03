package com.huertohogar.huertoauth.controller;

import com.huertohogar.huertoauth.dto.AuthResponse;
import com.huertohogar.huertoauth.dto.LoginRequest;
import com.huertohogar.huertoauth.dto.RegisterRequest;
import com.huertohogar.huertoauth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // permitir peticiones desde tu app Android
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
