package com.rural.platform.service;

import com.rural.platform.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> getUserCart(Long userId);
    ShoppingCart addToCart(ShoppingCart cart);
    boolean updateCartItemQuantity(Long cartId, Integer quantity);
    boolean removeFromCart(Long cartId);
    boolean clearUserCart(Long userId);
}

