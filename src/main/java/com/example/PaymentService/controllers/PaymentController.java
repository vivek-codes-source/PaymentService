package com.example.PaymentService.controllers;

import com.example.PaymentService.dtos.InititatePaymentDto;
import com.example.PaymentService.services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping

    public String initiatePayment(@RequestBody InititatePaymentDto inititatePaymentDto) {
        return paymentService.initiatePayment(inititatePaymentDto.getOrderId(),inititatePaymentDto.getEmail(),inititatePaymentDto.getPhoneNumber(),inititatePaymentDto.getAmount());
    }
}
