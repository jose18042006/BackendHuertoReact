package com.backend.huertohogarbackend.controller;

import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // --- ENDPOINTS PARA EL USUARIO ACTUAL ---

    /**
     * Obtiene los datos del usuario que ha iniciado sesión.
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Permite al usuario que ha iniciado sesión actualizar sus propios datos.
     */
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User updatedData) {
        return userService.updateUserProfile(userDetails.getUsername(), updatedData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ENDPOINTS DE ADMINISTRACIÓN (SUPERPODERES) ---

    /**
     * [ADMIN] Obtiene la lista de TODOS los usuarios.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * [ADMIN] Crea un nuevo usuario (admin, empleado o cliente).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUserByAdmin(@RequestBody User newUser) {
        // El servicio se encarga de encriptar la contraseña antes de guardar.
        return ResponseEntity.ok(userService.registerUserByAdmin(newUser));
    }

    /**
     * [ADMIN] Actualiza cualquier usuario por su ID.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserByAdmin(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.update(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * [ADMIN] Elimina un usuario por su ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserByAdmin(@PathVariable Long id) {
        if (userService.delete(id)) {
            return ResponseEntity.noContent().build(); // .noContent() es el estándar para un DELETE exitoso.
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}