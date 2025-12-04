package com.backend.huertohogarbackend.service;

import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscar el usuario en la base de datos por su nombre de usuario.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el nombre: " + username));

        // 2. Crear una lista de autoridades (roles) a partir del rol único del usuario.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));

        // 3. Devolver un objeto UserDetails estándar de Spring Security.
        //    No necesitamos una clase UserDetailsImpl personalizada.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
