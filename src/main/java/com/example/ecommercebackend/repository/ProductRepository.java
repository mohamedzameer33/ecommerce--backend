package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Only show active (non-deleted) products by default
    List<Product> findByActiveTrue();

    // Find a specific product only if it's active
    Optional<Product> findByIdAndActiveTrue(Long id);

    // Optional: see deleted/inactive products (for admin purposes)
    List<Product> findByActiveFalse();

    // Optional: check if product exists and is active
    boolean existsByIdAndActiveTrue(Long id);
}