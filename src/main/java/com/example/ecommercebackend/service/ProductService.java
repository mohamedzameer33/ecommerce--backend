package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.Product;
import com.example.ecommercebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Only return active products for customers
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    // For admin - can see all products (active + inactive)
    public List<Product> getAllProductsIncludingDeleted() {
        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(Product product) {
        product.setActive(true); // make sure new products are active
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Product not found or deleted with id: " + id));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (!product.isActive()) {
            throw new IllegalStateException("Product is already deleted/inactive");
        }

        product.setActive(false);
        productRepository.save(product);
    }

    // Optional: Restore deleted product (admin feature)
    @Transactional
    public void restoreProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (product.isActive()) {
            throw new IllegalStateException("Product is already active");
        }

        product.setActive(true);
        productRepository.save(product);
    }
}