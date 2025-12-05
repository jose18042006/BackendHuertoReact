// src/main/java/com/backend/huertohogarbackend/BackendApplication.java

package com.backend.huertohogarbackend;

import com.backend.huertohogarbackend.model.Producto;
import com.backend.huertohogarbackend.model.Role;
import com.backend.huertohogarbackend.model.User;
import com.backend.huertohogarbackend.repository.ProductoRepository; // <-- 1. IMPORTAMOS EL REPOSITORIO
import com.backend.huertohogarbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			ProductoRepository productoRepository, // <-- 2. INYECTAMOS EL REPOSITORIO
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			// --- Creación de Usuarios de Prueba ---
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

			// --- 3. CREACIÓN DE PRODUCTOS DE PRUEBA ---
			System.out.println(">>> Verificando y creando productos de prueba...");

			// Lista de productos a crear
			List<Producto> productos = List.of(
					new Producto("FR001", "frutas", "Manzanas Fuji", 1200.0, "kg", "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule.", "Valle del Maule, Chile", "150 kilos", "/imagenes/manzana_fuji.png"),
					new Producto("FR002", "frutas", "Naranjas Valencia", 1000.0, "kg", "Jugosas y ricas en vitamina C, ideales para zumos frescos.", "Valencia, España", "200 kilos", "/imagenes/naranja_valencia.png"),
					new Producto("FR003", "frutas", "Plátanos Cavendish", 800.0, "kg", "Plátanos maduros y dulces, perfectos para el desayuno.", "Ecuador", "250 kilos", "/imagenes/platano_cavendish.png"),
					new Producto("VR001", "verduras", "Zanahorias Orgánicas", 900.0, "kg", "Zanahorias crujientes cultivadas sin pesticidas.", "Región de O'Higgins, Chile", "100 kilos", "/imagenes/zanahoria.png"),
					new Producto("VR002", "verduras", "Espinacas Frescas", 700.0, "bolsa de 500g", "Espinacas frescas y nutritivas, perfectas para ensaladas.", "Cultivo Local", "80 bolsas", "/imagenes/espinaca.png"),
					new Producto("VR003", "verduras", "Pimientos Tricolores", 1500.0, "kg", "Pimientos rojos, amarillos y verdes, ideales para salteados.", "Cultivo Local", "120 kilos", "/imagenes/pimientos.png"),
					new Producto("PO001", "otros", "Miel Orgánica", 5000.0, "frasco de 500g", "Miel pura y orgánica producida por apicultores locales.", "Apicultores Locales", "50 frascos", "/imagenes/miel.png")
			);

			// Recorremos la lista y creamos cada producto solo si no existe
			for (Producto p : productos) {
				if (productoRepository.findById(p.getId()).isEmpty()) {
					productoRepository.save(p);
					System.out.println("    > Producto '" + p.getNombre() + "' creado.");
				}
			}
			System.out.println(">>> Verificación de productos finalizada.");
		};
	}
}