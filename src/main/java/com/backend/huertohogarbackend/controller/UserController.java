// src/main/java/com/backend/huertohogarbackend/controller/UserController.java

package com.backend.huertohogarbackend.controller;

import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*; // Asegúrate de tener este import

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ¡NUEVO ENDPOINT! ---
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User updatedData) {
        return userService.updateUserProfile(userDetails.getUsername(), updatedData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}