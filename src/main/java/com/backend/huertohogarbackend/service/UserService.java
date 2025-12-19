package com.backend.huertohogarbackend.service;

import com.backend.huertohogarbackend.dto.RegisterRequest;
import com.backend.huertohogarbackend.model.Role;
import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> updateUserProfile(String currentUsername, User updatedData) {
        return userRepository.findByUsername(currentUsername)
                .map(userToUpdate -> {
                    userToUpdate.setUsername(updatedData.getUsername());
                    userToUpdate.setEmail(updatedData.getEmail());
                    if (updatedData.getPassword() != null && !updatedData.getPassword().isEmpty()) {
                        userToUpdate.setPassword(passwordEncoder.encode(updatedData.getPassword()));
                    }
                    return userRepository.save(userToUpdate);
                });
    }

    // --- MÉTODO CORREGIDO Y ROBUSTO ---
    public User registerUserByAdmin(User user) {
        // Forzamos el ID a ser nulo para asegurar que Hibernate haga un INSERT (crear)
        // y no un UPDATE (actualizar), solucionando el StaleObjectStateException.
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User registerClient(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CLIENT)
                .build();
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> update(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setRole(userDetails.getRole());
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    return userRepository.save(user);
                });
    }

    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}