package com.irlix.irlix_week_four.controllers;

import com.irlix.irlix_week_four.dto.PaymentDTO;
import com.irlix.irlix_week_four.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/paymentList")
    public List<PaymentDTO> getPayments() {
        return paymentService.findAll();
    }

    @PostMapping
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO, @RequestParam(name = "sender_id") Long senderId, @RequestParam(name = "recipient_id") Long recipientId) {
        return paymentService.newPayment(paymentDTO, senderId, recipientId);
    }

    @PostMapping("/newTransfer")
    public void transfer(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestParam BigDecimal amount) {
        paymentService.transfer(senderId, recipientId, amount);
    }

    @GetMapping("/outgoing/{senderId}")
    public List<PaymentDTO> getOutgoingPayments(@PathVariable Long senderId) {
        return paymentService.getGoingPayments(senderId);
    }

    @GetMapping("/incoming/{recipientId}")
    public List<PaymentDTO> getIncomingPayments(@PathVariable Long recipientId) {
        return paymentService.getIncomingPayments(recipientId);
    }
}
