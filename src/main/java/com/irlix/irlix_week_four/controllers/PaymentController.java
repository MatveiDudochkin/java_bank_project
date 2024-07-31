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
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO, @RequestParam(name = "senderId") long senderId, @RequestParam(name = "recipientId") long recipientId) {
        return paymentService.newPayment(paymentDTO, senderId, recipientId);
    }

    @PostMapping("/newTransfer")
    public void transfer(@RequestParam long senderId, @RequestParam long recipientId, @RequestParam BigDecimal amount) {
        paymentService.transfer(senderId, recipientId, amount);
    }

    @GetMapping("/outgoing/{senderId}")
    public List<PaymentDTO> getOutgoingPayments(@PathVariable long senderId) {
        return paymentService.getGoingPayments(senderId);
    }

    @GetMapping("/incoming/{recipientId}")
    public List<PaymentDTO> getIncomingPayments(@PathVariable long recipientId) {
        return paymentService.getIncomingPayments(recipientId);
    }
}
