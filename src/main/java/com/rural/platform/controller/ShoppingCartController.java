package com.rural.platform.controller;

import com.rural.platform.entity.ShoppingCart;
import com.rural.platform.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartService cartService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCart>> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }
    
    @PostMapping
    public ResponseEntity<ShoppingCart> addToCart(@RequestBody ShoppingCart cart) {
        return ResponseEntity.ok(cartService.addToCart(cart));
    }
    
    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @PathVariable Long cartId,
            @RequestParam Integer quantity) {
        boolean updated = cartService.updateCartItemQuantity(cartId, quantity);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        boolean removed = cartService.removeFromCart(cartId);
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearUserCart(@PathVariable Long userId) {
        boolean cleared = cartService.clearUserCart(userId);
        return cleared ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
} 