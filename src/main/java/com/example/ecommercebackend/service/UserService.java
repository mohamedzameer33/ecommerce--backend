package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.User;
import com.example.ecommercebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && password != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}