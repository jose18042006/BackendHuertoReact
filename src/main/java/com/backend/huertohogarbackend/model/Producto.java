package com.backend.huertohogarbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    private String id;

    private String categoria;
    private String nombre;
    private double precio; // Cambiado a tipo primitivo para eficiencia
    private String unidad;
    private String descripcion;
    private String origen;
    private String stock;
    private String imagen;
}
