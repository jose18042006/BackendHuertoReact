package com.backend.huertohogarbackend.controller;

import com.backend.huertohogarbackend.security.jwt.JwtService;
import com.backend.huertohogarbackend.service.UserService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    public AuthController(AuthenticationManager authManager, JwtService jwtService,
                          UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String address = body.get("address");
        String phoneNumber = body.get("phoneNumber");
        if (email == null || password == null || address == null || phoneNumber == null ||
                email.isBlank() || password.isBlank() || address.isBlank() || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Todos los campos son requeridos");
        }
        userService.register(email, password, address, phoneNumber);
        return Map.of("message", "Usuario registrado correctamente");
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(email);
            return Map.of("token", token);
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}