package com.example.ecommercebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommercebackend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}