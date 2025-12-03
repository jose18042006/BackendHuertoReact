package com.backend.huertohogarbackend.repository;

import com.backend.huertohogarbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para buscar un usuario por su nombre de usuario.
    // Esencial para el proceso de login de Spring Security.
    Optional<User> findByUsername(String username);

    // Método para buscar un usuario por su email (útil para otras funcionalidades).
    Optional<User> findByEmail(String email);
}
