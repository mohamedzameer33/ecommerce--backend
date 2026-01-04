package com.example.ecommercebackend.entity;import jakarta.persistence.*;
import lombok.Data;@Entity

@Data

public class Product {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private double price;
private String description;
private int stock;
private boolean active = true;

@Column(length = 2048)  // Increased for long URLs (2048 is safe for most URLs)
private String imageUrl;}

