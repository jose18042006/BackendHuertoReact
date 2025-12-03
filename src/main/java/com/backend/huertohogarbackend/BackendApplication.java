package com.backend.huertohogarbackend;

import com.backend.huertohogarbackend.model.Role;
import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Crear un usuario ADMIN por defecto si no existe
			if (userRepository.findByUsername("adminuser").isEmpty()) {
				User adminUser = User.builder()
						.username("adminuser")
						.email("admin@huertohogar.com")
						.password(passwordEncoder.encode("adminpass"))
						.role(Role.ROLE_ADMIN)
						.build();
				userRepository.save(adminUser);
				System.out.println(">>> Usuario ADMIN de prueba 'adminuser' creado.");
			}

			// Crear un usuario CLIENT por defecto si no existe
			if (userRepository.findByUsername("clientuser").isEmpty()) {
				User clientUser = User.builder()
						.username("clientuser")
						.email("client@example.com")
						.password(passwordEncoder.encode("clientpass"))
						.role(Role.ROLE_CLIENT)
						.build();
				userRepository.save(clientUser);
				System.out.println(">>> Usuario CLIENT de prueba 'clientuser' creado.");
			}
		};
	}
}
