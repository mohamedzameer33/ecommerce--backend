package com.example.ecommercebackend.service;

import com.example.ecommercebackend.entity.Order;
import com.example.ecommercebackend.entity.Product;
import com.example.ecommercebackend.entity.User;
import com.example.ecommercebackend.repository.OrderRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order placeOrder(Order incomingOrder) {
        if (incomingOrder.getUser() == null || incomingOrder.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (incomingOrder.getProduct() == null || incomingOrder.getProduct().getId() == null) {
            throw new IllegalArgumentException("Product ID is required");
        }
        if (incomingOrder.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        User user = userRepository.findById(incomingOrder.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(incomingOrder.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < incomingOrder.getQuantity()) {
            throw new RuntimeException("Not enough stock. Available: " + product.getStock());
        }

        // Decrease stock
        product.setStock(product.getStock() - incomingOrder.getQuantity());
        productRepository.save(product);

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(incomingOrder.getQuantity());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        return orderRepository.save(order);
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Only PENDING orders can be completed");
        }

        order.setStatus("COMPLETED");
        orderRepository.save(order);
    }

    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus("PENDING");
    }

    public List<Order> getCompletedOrders() {
        return orderRepository.findByStatus("COMPLETED");
    }
}