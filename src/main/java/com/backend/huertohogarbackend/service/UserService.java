package com.backend.huertohogarbackend.service;

import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User register(String email, String password, String address, String phoneNumber) {
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .Address(address)
                .phoneNumber(phoneNumber)
                .build();
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}