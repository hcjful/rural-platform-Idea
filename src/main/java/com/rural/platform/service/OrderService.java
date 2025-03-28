package com.rural.platform.service;

import com.rural.platform.entity.Order;
import com.rural.platform.entity.OrderDetail;
import com.rural.platform.entity.ShoppingCart;

import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);
    List<Order> getUserOrders(Long userId);
    Order createOrder(Long userId, String shippingAddress, List<ShoppingCart> cartItems);
    boolean updateOrderStatus(Long orderId, String status);
    boolean cancelOrder(Long orderId);
    List<OrderDetail> getOrderDetails(Long orderId);
}

