package com.backend.huertohogarbackend.service;

import com.backend.huertohogarbackend.model.ERole;
import com.backend.huertohogarbackend.model.Role;
import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.RoleRepository;
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public User register(String email, String password, String address, String phoneNumber) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .Address(address)
                .phoneNumber(phoneNumber)
                .roles(roles)
                .build();
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}
