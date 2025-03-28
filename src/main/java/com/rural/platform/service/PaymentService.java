package com.rural.platform.service;

import com.rural.platform.entity.Payment;
import com.rural.platform.entity.Order;

public interface PaymentService {
    Payment createPayment(Order order, String paymentMethod);
    Payment getPaymentByOrderId(Long orderId);
    boolean processPayment(String paymentNo, String transactionId);
    void cancelPayment(String paymentNo);
}

