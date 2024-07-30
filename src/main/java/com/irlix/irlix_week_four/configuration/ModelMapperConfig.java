package com.irlix.irlix_week_four.configuration;

import com.irlix.irlix_week_four.dto.*;
import com.irlix.irlix_week_four.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public ClientDTO mapClientDto(ClientEntities clientEntities) {
        return modelMapper().map(clientEntities, ClientDTO.class);
    }

    public PaymentDTO mapPaymentDto(PaymentEntities paymentEntities) {
        return modelMapper().map(paymentEntities, PaymentDTO.class);
    }

    public ClientEntities mapClientEntity(ClientDTO clientDTO) {
        return modelMapper().map(clientDTO, ClientEntities.class);
    }

    public PaymentEntities mapPaymentEntity(PaymentDTO paymentDTO) {
        return modelMapper().map(paymentDTO, PaymentEntities.class);
    }
}