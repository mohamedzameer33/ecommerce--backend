package com.example.ecommercebackend.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")  // ← FIXED: using "orders" instead of reserved word "order"
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"orders", "password", "email", "role"})  // prevent serialization issues
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"stock", "description", "category", "orders"})
    private Product product;

    private int quantity;

    private String status;  // "PENDING", "COMPLETED"

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}