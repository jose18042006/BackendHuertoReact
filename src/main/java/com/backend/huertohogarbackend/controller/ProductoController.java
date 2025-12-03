package com.backend.huertohogarbackend.controller;

import com.backend.huertohogarbackend.model.Producto;
import com.backend.huertohogarbackend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllProductos() {
        // CORREGIDO: Llamar a findAll()
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable String id) {
        // CORREGIDO: Llamar a findById()
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public Producto createProducto(@RequestBody Producto producto) {
        // CORREGIDO: Llamar a save()
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Producto> updateProducto(@PathVariable String id, @RequestBody Producto productoDetails) {
        // CORREGIDO: Llamar a findById() y save()
        return productoService.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setCategoria(productoDetails.getCategoria());
                    producto.setPrecio(productoDetails.getPrecio());
                    producto.setUnidad(productoDetails.getUnidad());
                    producto.setDescripcion(productoDetails.getDescripcion());
                    producto.setOrigen(productoDetails.getOrigen());
                    producto.setStock(productoDetails.getStock());
                    producto.setImagen(productoDetails.getImagen());
                    Producto updatedProducto = productoService.save(producto);
                    return ResponseEntity.ok(updatedProducto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Void> deleteProducto(@PathVariable String id) {
        // CORREGIDO: Llamar a findById() y deleteById()
        return productoService.findById(id)
                .map(producto -> {
                    productoService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
