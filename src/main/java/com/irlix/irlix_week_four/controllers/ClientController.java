package com.irlix.irlix_week_four.controllers;

import com.irlix.irlix_week_four.dto.ClientDTO;
import com.irlix.irlix_week_four.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/show")
    public List<ClientDTO> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/show/{id}")
    public ClientDTO getClient(@PathVariable long id) {
        return clientService.findById(id);
    }

    @PostMapping("/new")
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.saveNewClient(clientDTO);
    }

    @PutMapping("/edit/{id}")
    public ClientDTO updateClient(@PathVariable long id, @RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(id, clientDTO);
    }

    @DeleteMapping({"/{id}"})
    private void deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
    }
}
