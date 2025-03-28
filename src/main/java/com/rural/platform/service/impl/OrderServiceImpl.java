package com.rural.platform.service.impl;

import com.rural.platform.entity.Order;
import com.rural.platform.entity.OrderDetail;
import com.rural.platform.entity.Product;
import com.rural.platform.entity.ShoppingCart;
import com.rural.platform.mapper.OrderDetailMapper;
import com.rural.platform.mapper.OrderMapper;
import com.rural.platform.mapper.ProductMapper;
import com.rural.platform.service.OrderService;
import com.rural.platform.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShoppingCartService cartService;

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.findById(id);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order createOrder(Long userId, String shippingAddress, List<ShoppingCart> cartItems) {
        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setShippingAddress(shippingAddress);
        order.setStatus("PENDING");

        // 计算总金额并创建订单详情
        double totalAmount = 0;
        for (ShoppingCart cartItem : cartItems) {
            Product product = productMapper.findById(cartItem.getProductId());
            if (product == null || product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("商品库存不足");
            }

            totalAmount += product.getPrice().doubleValue() * cartItem.getQuantity();

            // 更新库存
            productMapper.updateStock(product.getId(), product.getStock() - cartItem.getQuantity());

            // 创建订单详情
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setProductId(product.getId());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(product.getPrice());
            orderDetailMapper.insert(detail);
        }

        order.setTotalAmount(new java.math.BigDecimal(totalAmount));
        orderMapper.insert(order);

        // 清空购物车
        cartService.clearUserCart(userId);

        return order;
    }

    @Override
    public boolean updateOrderStatus(Long orderId, String status) {
        return orderMapper.updateStatus(orderId, status) > 0;
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null || !"PENDING".equals(order.getStatus())) {
            return false;
        }

        // 恢复库存
        List<OrderDetail> details = orderDetailMapper.findByOrderId(orderId);
        for (OrderDetail detail : details) {
            Product product = productMapper.findById(detail.getProductId());
            productMapper.updateStock(product.getId(), product.getStock() + detail.getQuantity());
        }

        // 更新订单状态
        return orderMapper.updateStatus(orderId, "CANCELLED") > 0;
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }
}
