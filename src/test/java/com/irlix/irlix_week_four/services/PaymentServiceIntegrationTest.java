package com.irlix.irlix_week_four.services;

import com.irlix.irlix_week_four.entities.*;
import com.irlix.irlix_week_four.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PaymentServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("payments")
            .withUsername("postgres")
            .withPassword("5255742");

    @BeforeEach
    public void setUp() {
        paymentRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void testGetGoingPayments() throws Exception {
        ClientEntities sender = new ClientEntities();
        sender.setName("John Doe");
        sender.setPhoneNumber("1234567890");
        sender.setBalance(BigDecimal.valueOf(1000.0));
        sender = clientRepository.save(sender);

        ClientEntities recipient = new ClientEntities();
        recipient.setName("Jane Doe");
        recipient.setPhoneNumber("0987654321");
        recipient.setBalance(BigDecimal.valueOf(2000.0));
        recipient = clientRepository.save(recipient);

        PaymentEntities payment = new PaymentEntities();
        payment.setAmount(BigDecimal.valueOf(100.0));
        payment.setDate(LocalDateTime.now());
        payment.setSender(sender);
        payment.setRecipient(recipient);
        payment.setMessage("Outgoing Payment");
        paymentRepository.save(payment);

        mockMvc.perform(get("/outgoing/{senderId}", sender.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message").value("Outgoing Payment"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }

    @Test
    public void testGetIncomingPayments() throws Exception {
        ClientEntities sender = new ClientEntities();
        sender.setName("John Doe");
        sender.setPhoneNumber("1234567890");
        sender.setBalance(BigDecimal.valueOf(1000.0));
        sender = clientRepository.save(sender);

        ClientEntities recipient = new ClientEntities();
        recipient.setName("Jane Doe");
        recipient.setPhoneNumber("0987654321");
        recipient.setBalance(BigDecimal.valueOf(2000.0));
        recipient = clientRepository.save(recipient);

        PaymentEntities payment = new PaymentEntities();
        payment.setAmount(BigDecimal.valueOf(100.0));
        payment.setDate(LocalDateTime.now());
        payment.setSender(sender);
        payment.setRecipient(recipient);
        payment.setMessage("Incoming Payment");
        paymentRepository.save(payment);

        mockMvc.perform(get("/incoming/{recipientId}", recipient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message").value("Incoming Payment"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }
}
