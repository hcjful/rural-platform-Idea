package com.rural.platform.controller;

import com.rural.platform.entity.Order;
import com.rural.platform.entity.OrderDetail;
import com.rural.platform.entity.ShoppingCart;
import com.rural.platform.service.OrderService;
import com.rural.platform.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ShoppingCartService cartService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }
    
    @PostMapping("/user/{userId}")
    public ResponseEntity<Order> createOrder(
            @PathVariable Long userId,
            @RequestParam String shippingAddress) {
        List<ShoppingCart> cartItems = cartService.getUserCart(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(orderService.createOrder(userId, shippingAddress, cartItems));
    }
    
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        boolean updated = orderService.updateOrderStatus(orderId, status);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        boolean cancelled = orderService.cancelOrder(orderId);
        return cancelled ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
} 