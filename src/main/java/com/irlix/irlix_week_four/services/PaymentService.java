package com.irlix.irlix_week_four.services;

import com.irlix.irlix_week_four.configuration.ModelMapperConfig;
import com.irlix.irlix_week_four.dto.ClientDTO;
import com.irlix.irlix_week_four.dto.PaymentDTO;
import com.irlix.irlix_week_four.entities.ClientEntities;
import com.irlix.irlix_week_four.entities.PaymentEntities;
import com.irlix.irlix_week_four.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final ModelMapperConfig modelMapperConfig;
    private final ClientService clientService;

    public PaymentService(PaymentRepository paymentRepository, ClientRepository clientRepository, ModelMapperConfig modelMapperConfig, ClientService clientService) {
        this.paymentRepository = paymentRepository;
        this.clientRepository = clientRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.clientService = clientService;
    }

    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll().stream().map(modelMapperConfig::mapPaymentDto).collect(Collectors.toList());
    }

    public PaymentDTO newPayment(PaymentDTO paymentDTO, long senderId, long recipientId) {
        ClientEntities sender = clientRepository.findById(senderId).orElse(null);
        ClientEntities recipient = clientRepository.findById(recipientId).orElse(null);
        PaymentEntities payment = modelMapperConfig.mapPaymentEntity(paymentDTO);
        payment.setSender(sender);
        payment.setRecipient(recipient);
        payment = paymentRepository.save(payment);
        return modelMapperConfig.mapPaymentDto(payment);
    }

    @Transactional
    public void transfer(long senderId, long recipientId, BigDecimal amount) {
        ClientDTO sender = clientService.findById(senderId);
        ClientDTO recipient = clientService.findById(recipientId);

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));

        modelMapperConfig.mapClientEntity(clientService.updateClient(sender.getId(), sender));
        modelMapperConfig.mapClientEntity(clientService.updateClient(recipient.getId(), recipient));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDate(LocalDateTime.now());
        paymentDTO.setAmount(amount);
        paymentDTO.setRecipientId(recipient.getId());
        paymentDTO.setSenderId(sender.getId());
        paymentDTO.setMessage("New transfer from " + sender.getName() + " to " + recipient.getName());
        modelMapperConfig.mapPaymentEntity(newPayment(paymentDTO, recipient.getId(), sender.getId()));
    }

    public List<PaymentDTO> getGoingPayments(long senderId) {
        ClientEntities sender = clientRepository.findById(senderId).orElse(null);
        List<PaymentEntities> outPayments = paymentRepository.findAllBySender(sender);
        return outPayments.stream().map(modelMapperConfig::mapPaymentDto).collect(Collectors.toList());
    }

    public List<PaymentDTO> getIncomingPayments(long recipientId) {
        ClientEntities recipient = clientRepository.findById(recipientId).orElse(null);
        List<PaymentEntities> incomingPayments = paymentRepository.findAllByRecipient(recipient);
        return incomingPayments.stream().map(modelMapperConfig::mapPaymentDto).collect(Collectors.toList());
    }
}
