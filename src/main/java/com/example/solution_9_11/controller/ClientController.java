package com.example.solution_9_11.controller;

import com.example.solution_9_11.ResponsesWithData;
import com.example.solution_9_11.domain.Client;
import com.example.solution_9_11.dto.ClientRequest;
import com.example.solution_9_11.dto.ClientUpdateRequest;
import com.example.solution_9_11.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findClient(@PathVariable String id) {
        Client client = clientService.findClientById(Long.parseLong(id));
        return ResponseEntity.ok(new ResponsesWithData(HttpStatus.OK.value(), "OK", client));
    }

    @PostMapping("/create")
    ResponseEntity<?> postClient(@RequestBody ClientRequest data) {
        Client client = data.client;
        return ResponseEntity.ok(clientService.createClient(client.getName(), client.getSurname(), client.getBirthDate(), data.tourName));
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> updateClient(@RequestBody ClientUpdateRequest data, @PathVariable String id) {
        return ResponseEntity.ok(clientService.updateClient(Long.valueOf(id), data));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteClient(@PathVariable String id) {
        return ResponseEntity.ok(clientService.deleteClient(Long.parseLong(id)));
    }
}
