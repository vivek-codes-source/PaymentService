package com.example.PaymentService.services;

import com.example.PaymentService.paymentgateway.PaymentGateway;
import com.example.PaymentService.paymentgateway.PaymentGatewayStrategyChooser;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentGatewayStrategyChooser paymentGatewayStrategyChooser;

    public PaymentService(PaymentGatewayStrategyChooser paymentGatewayStrategyChooser) {
        this.paymentGatewayStrategyChooser = paymentGatewayStrategyChooser;
    }

    public String initiatePayment(String orderId, String email, String phoneNumber, Long amount) {
        PaymentGateway paymentGateway = paymentGatewayStrategyChooser.getBestPaymentGateway();
        return paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);
    }
}