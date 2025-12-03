package com.backend.huertohogarbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String categoria;
    private String nombre;
    private Double precio;
    private String unidad;
    private String descripcion;
    private String origen;
    private String stock;
    private String imagen;
}
