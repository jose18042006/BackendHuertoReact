package com.huertohogar.huertoauth.service;

import com.huertohogar.huertoauth.dto.AuthResponse;
import com.huertohogar.huertoauth.dto.LoginRequest;
import com.huertohogar.huertoauth.dto.RegisterRequest;
import com.huertohogar.huertoauth.model.User;
import com.huertohogar.huertoauth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. comprobar si el email ya existe
        if (userRepository.findByEmail(request.email).isPresent()) {
            return new AuthResponse(false, "El email ya está registrado", null, null, null);
        }

        // 2. crear usuario nuevo
        User user = new User();
        user.setNombre(request.nombre);
        user.setEmail(request.email);
        user.setPassword(request.password);  // en real: encriptar

        user = userRepository.save(user);

        // 3. generar un token "falso" de ejemplo
        String token = UUID.randomUUID().toString();

        return new AuthResponse(
                true,
                "Usuario registrado correctamente",
                token,
                user.getId(),
                user.getNombre()
        );
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email);

        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "Usuario no encontrado", null, null, null);
        }

        User user = userOpt.get();

        System.out.println("Comparando passwords: DB=" + user.getPassword() + " - Input=" + request.password);
        if (!user.getPassword().trim().equals(request.password.trim())) {
            return new AuthResponse(false, "Contraseña incorrecta", null, null, null);
        }


        String token = UUID.randomUUID().toString();

        return new AuthResponse(
                true,
                "Login correcto",
                token,
                user.getId(),
                user.getNombre()
        );
    }
}
