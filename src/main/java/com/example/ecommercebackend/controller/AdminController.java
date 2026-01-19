package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.entity.Order;
import com.example.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "https://zam-ecomm.vercel.app")
public class AdminController {
    @Autowired
    private OrderService orderService;

    // Simple hardcoded admin login (improve in prod)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        if ("admin".equals(credentials.get("username")) && "````".equals(credentials.get("password"))) {
            return ResponseEntity.ok("Logged in");
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @GetMapping("/orders/pending")
    public List<Order> getPending() {
        return orderService.getPendingOrders();
    }

    @GetMapping("/orders/completed")
    public List<Order> getCompleted() {
        return orderService.getCompletedOrders();
    }
}
