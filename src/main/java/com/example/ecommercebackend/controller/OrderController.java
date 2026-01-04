package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.entity.Order;
import com.example.ecommercebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;


    
    @PostMapping
    public Order place(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @PostMapping("/{id}/complete")
    public void complete(@PathVariable Long id) {
        orderService.completeOrder(id);
    }
}