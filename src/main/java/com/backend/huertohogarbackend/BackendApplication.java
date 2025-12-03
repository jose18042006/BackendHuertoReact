package com.backend.huertohogarbackend;

import com.backend.huertohogarbackend.model.ERole;
import com.backend.huertohogarbackend.model.Role;
import com.backend.huertohogarbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
				roleRepository.save(Role.builder().name(ERole.ROLE_USER).build());
			}
			if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
				roleRepository.save(Role.builder().name(ERole.ROLE_ADMIN).build());
			}
		};
	}
}
