package com.irlix.irlix_week_four.services;

import com.irlix.irlix_week_four.dto.ClientDTO;
import com.irlix.irlix_week_four.entities.ClientEntities;
import com.irlix.irlix_week_four.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import com.irlix.irlix_week_four.configuration.ModelMapperConfig;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapperConfig modelMapperConfig;

    public ClientService(ClientRepository clientRepository, ModelMapperConfig modelMapperConfig) {
        this.clientRepository = clientRepository;
        this.modelMapperConfig = modelMapperConfig;
    }

    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(modelMapperConfig::mapClientDto).collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) {
        return modelMapperConfig.mapClientDto(clientRepository.findById(id).orElse(new ClientEntities()));
    }

    public ClientDTO saveNewClient(ClientDTO clientDTO) {
        ClientEntities clientEntities = modelMapperConfig.mapClientEntity(clientDTO);
        clientEntities = clientRepository.save(clientEntities);
        return modelMapperConfig.mapClientDto(clientEntities);
    }

    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        ClientEntities clientEntities = clientRepository.findById(id).orElse(null);
        clientEntities.setName(clientDTO.getName());
        clientEntities.setPhoneNumber(clientDTO.getPhoneNumber());
        clientEntities.setBalance(clientDTO.getBalance());
        clientRepository.save(clientEntities);
        return modelMapperConfig.mapClientDto(clientEntities);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
