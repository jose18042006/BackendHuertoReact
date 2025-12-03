package com.backend.huertohogarbackend.repository;

import com.backend.huertohogarbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
