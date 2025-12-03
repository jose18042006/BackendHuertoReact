package com.backend.huertohogarbackend.controller;

import com.backend.huertohogarbackend.model.Product;
import com.backend.huertohogarbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product Management System")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "View a list of available products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by Id")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new product")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public Product updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            existingProduct.setCategoria(productDetails.getCategoria());
            existingProduct.setNombre(productDetails.getNombre());
            existingProduct.setPrecio(productDetails.getPrecio());
            existingProduct.setUnidad(productDetails.getUnidad());
            existingProduct.setDescripcion(productDetails.getDescripcion());
            existingProduct.setOrigen(productDetails.getOrigen());
            existingProduct.setStock(productDetails.getStock());
            existingProduct.setImagen(productDetails.getImagen());
            return productService.saveProduct(existingProduct);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
