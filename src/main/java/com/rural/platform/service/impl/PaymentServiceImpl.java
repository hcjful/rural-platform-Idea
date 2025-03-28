package com.rural.platform.service.impl;

import com.rural.platform.entity.Order;
import com.rural.platform.entity.Payment;
import com.rural.platform.mapper.OrderMapper;
import com.rural.platform.mapper.PaymentMapper;
import com.rural.platform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Payment createPayment(Order order, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setPaymentNo("PAY" + UUID.randomUUID().toString().replace("-", ""));
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("PENDING");

        paymentMapper.insert(payment);
        return payment;
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentMapper.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public boolean processPayment(String paymentNo, String transactionId) {
        Payment payment = paymentMapper.findByPaymentNo(paymentNo);
        if (payment == null || !"PENDING".equals(payment.getStatus())) {
            return false;
        }

        payment.setStatus("SUCCESS");
        payment.setTransactionId(transactionId);
        payment.setPaidAt(LocalDateTime.now());
        paymentMapper.updateStatus(payment);

        // 更新订单状态
        orderMapper.updateStatus(payment.getOrderId(), "PAID");

        return true;
    }

    @Override
    @Transactional
    public void cancelPayment(String paymentNo) {
        Payment payment = paymentMapper.findByPaymentNo(paymentNo);
        if (payment != null && "PENDING".equals(payment.getStatus())) {
            payment.setStatus("CANCELLED");
            paymentMapper.updateStatus(payment);
        }
    }
}
