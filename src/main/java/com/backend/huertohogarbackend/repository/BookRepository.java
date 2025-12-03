package com.backend.huertohogarbackend.repository;

import com.backend.huertohogarbackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
