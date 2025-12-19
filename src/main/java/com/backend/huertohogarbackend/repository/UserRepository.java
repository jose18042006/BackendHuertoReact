package com.backend.huertohogarbackend.repository;

import com.backend.huertohogarbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- ¡IMPORT NECESARIO!
import org.springframework.data.repository.query.Param; // <-- ¡IMPORT NECESARIO!
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // --- ¡MÉTODO CORREGIDO CON QUERY EXPLÍCITA! ---
    /**
     * Busca un usuario por su nombre de usuario.
     * Añadimos una @Query explícita para asegurar que la consulta SQL sea la correcta
     * y evitar el error "NonUniqueResultException".
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    // Método para buscar un usuario por su email (útil para otras funcionalidades).
    // También le añadimos la query explícita por seguridad.
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}