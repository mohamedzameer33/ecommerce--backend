package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.entity.Product;
import com.example.ecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Product product) {
        try {
            Product saved = productService.addProduct(product);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();  // Logs the error
            return ResponseEntity
                .status(400)
                .body("Failed to add product: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // ProductController.java
@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        e.printStackTrace();  // ← will show in console
        return ResponseEntity
            .status(500)
            .body("Delete failed: " + e.getMessage());
    }
}
}