package com.huertohogar.huertoauth.dto;

public class AuthResponse {
    public boolean success;
    public String message;
    public String token;
    public Long userId;
    public String nombre;

    public AuthResponse(boolean success, String message, String token, Long userId, String nombre) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.nombre = nombre;
    }
}
