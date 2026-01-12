package com.example.PaymentService.paymentgateway;

public interface PaymentGateway {
    String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount);

}
