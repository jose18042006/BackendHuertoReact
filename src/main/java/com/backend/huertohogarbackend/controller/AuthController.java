package com.backend.huertohogarbackend.controller;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import org.springframework.security.core.Authentication;

import com.backend.huertohogarbackend.dto.RegisterRequest;
import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.security.jwt.JwtService;
import com.backend.huertohogarbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    // DENTRO DE TU AuthController.java
    // Asegúrate de que tienes este import al principio del archivo
// Y este también

// ... dentro de tu AuthController.java

    // Asegúrate de tener estos imports al principio del archivo


// ... dentro de tu AuthController.java

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest authRequest) {
        // 1. Autentica al usuario. Si falla, lanza una excepción.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // 2. Si la autenticación es exitosa, obtenemos el objeto UserDetails.
        //    Esto ahora funciona porque CustomUserDetailsService devuelve un UserDetails.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Generamos el token, que ahora incluirá el ROL.
        String token = jwtService.generateToken(userDetails);

        // 4. Devolvemos el token.
        return Map.of("token", token);
    }

// DENTRO DE AuthController.java

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println(">>> [AuthController] Endpoint /register alcanzado.");

        // Verificamos si los datos del request llegan bien
        if (registerRequest == null || registerRequest.getUsername() == null) {
            System.out.println("!!! [AuthController] ERROR: El cuerpo de la petición (RegisterRequest) es nulo o inválido.");
            return ResponseEntity.badRequest().body("Datos de registro inválidos.");
        }
        System.out.println(">>> [AuthController] Datos recibidos para el usuario: " + registerRequest.getUsername());

        try {
            User newUser = userService.registerClient(registerRequest);
            System.out.println(">>> [AuthController] El servicio ha creado el usuario. Devolviendo respuesta OK.");
            // Devolvemos el mensaje de éxito original
            return ResponseEntity.ok("Usuario '" + newUser.getUsername() + "' registrado exitosamente como cliente.");
        } catch (Exception e) {
            System.out.println("!!! [AuthController] ERROR capturado desde el servicio: " + e.getMessage());
            // Devolvemos el mensaje de error específico
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

// Clase auxiliar para el cuerpo de la petición de login
class AuthRequest {
    private String username;
    private String password;

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
