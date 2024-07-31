package com.irlix.irlix_week_four.services;

import com.irlix.irlix_week_four.configuration.ModelMapperConfig;
import com.irlix.irlix_week_four.dto.ClientDTO;
import com.irlix.irlix_week_four.entities.ClientEntities;
import com.irlix.irlix_week_four.repositories.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ModelMapperConfig modelMapperConfig;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void testFindAll() {
        ClientEntities client1 = new ClientEntities();
        client1.setId(1L);
        client1.setName("Valera Baranov");
        ClientEntities client2 = new ClientEntities();
        client1.setId(2L);
        client1.setName("Liza Voronkova");

        List<ClientEntities> clients = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setId(1L);
        clientDTO1.setName("Katya Kirillova");
        ClientDTO clientDTO2 = new ClientDTO();
        clientDTO2.setId(2L);
        clientDTO2.setName("Polina Merzlyakova");

        when(modelMapperConfig.mapClientDto(client1)).thenReturn(clientDTO1);
        when(modelMapperConfig.mapClientDto(client2)).thenReturn(clientDTO2);

        List<ClientDTO> clientDTOs = clientService.findAll();

        Assertions.assertNotNull(clientDTOs);
        Assertions.assertEquals(clientDTOs.size(), 2);
        Assertions.assertEquals("Katya Kirillova", clientDTOs.get(0).getName());
        Assertions.assertEquals("Polina Merzlyakova", clientDTOs.get(1).getName());

        verify(clientRepository, times(1)).findAll();
        verify(modelMapperConfig, times(1)).mapClientDto(client1);
        verify(modelMapperConfig, times(1)).mapClientDto(client2);
    }

    @Test
    public void testFindById() {
        ClientEntities client1 = new ClientEntities();
        client1.setId(1L);
        client1.setName("Valera Baranov");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));

        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setId(1L);
        clientDTO1.setName("Katya Kirillova");

        when(modelMapperConfig.mapClientDto(client1)).thenReturn(clientDTO1);

        ClientDTO result = clientService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Katya Kirillova", result.getName());

        verify(clientRepository, times(1)).findById(1L);
        verify(modelMapperConfig, times(1)).mapClientDto(client1);
    }

    @Test
    public void testSave() {
        ClientEntities client1 = new ClientEntities();
        client1.setName("Olya Kirillova");
        client1.setPhoneNumber("2412526346");
        client1.setBalance(new BigDecimal("160.00"));

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Olya Kirillova");
        clientDTO.setPhoneNumber("2412526346");
        clientDTO.setBalance(new BigDecimal("160.00"));

        when(modelMapperConfig.mapClientEntity(clientDTO)).thenReturn(client1);
        when(clientRepository.save(client1)).thenReturn(client1);
        when(modelMapperConfig.mapClientDto(client1)).thenReturn(clientDTO);

        ClientDTO result = clientService.saveNewClient(clientDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Olya Kirillova", result.getName());
        Assertions.assertEquals("2412526346", result.getPhoneNumber());
        Assertions.assertEquals(new BigDecimal("160.00"), result.getBalance());

        verify(modelMapperConfig, times(1)).mapClientEntity(clientDTO);
        verify(clientRepository, times(1)).save(client1);
        verify(modelMapperConfig, times(1)).mapClientDto(client1);
    }

    @Test
    public void testUpdate() {
        ClientEntities client = new ClientEntities();
        client.setId(1L);
        client.setName("Katya Kirillova");
        client.setPhoneNumber("1234567890");
        client.setBalance(new BigDecimal("1000.0"));

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("Polina Merzlyakova");
        clientDTO.setPhoneNumber("0987654321");
        clientDTO.setBalance(new BigDecimal("24214.4"));

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        when(modelMapperConfig.mapClientDto(client)).thenReturn(clientDTO);

        ClientDTO result = clientService.updateClient(1L, clientDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Polina Merzlyakova", result.getName());
        Assertions.assertEquals("0987654321", result.getPhoneNumber());
        Assertions.assertEquals(new BigDecimal("24214.4"), result.getBalance());

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(client);
        verify(modelMapperConfig, times(1)).mapClientDto(client);
    }

    @Test
    public void testDeleteClient() {
        clientService.deleteClient(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}
