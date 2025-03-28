package com.rural.platform.service.impl;

import com.rural.platform.entity.ShoppingCart;
import com.rural.platform.mapper.ShoppingCartMapper;
import com.rural.platform.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper cartMapper;

    @Override
    public List<ShoppingCart> getUserCart(Long userId) {
        return cartMapper.findByUserId(userId);
    }

    @Override
    public ShoppingCart addToCart(ShoppingCart cart) {
        ShoppingCart existingItem = cartMapper.findByUserIdAndProductId(cart.getUserId(), cart.getProductId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cart.getQuantity());
            cartMapper.updateQuantity(existingItem.getId(), existingItem.getQuantity());
            return existingItem;
        }
        cartMapper.insert(cart);
        return cart;
    }

    @Override
    public boolean updateCartItemQuantity(Long cartId, Integer quantity) {
        return cartMapper.updateQuantity(cartId, quantity) > 0;
    }

    @Override
    public boolean removeFromCart(Long cartId) {
        return cartMapper.deleteById(cartId) > 0;
    }

    @Override
    public boolean clearUserCart(Long userId) {
        return cartMapper.deleteByUserId(userId) > 0;
    }
}
