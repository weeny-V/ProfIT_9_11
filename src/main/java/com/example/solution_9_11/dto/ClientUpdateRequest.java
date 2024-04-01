package com.example.solution_9_11.dto;

import com.example.solution_9_11.domain.Client;

public class ClientUpdateRequest {
    public Client client;
    public String tourId;

    public ClientUpdateRequest(Client client, String tourId) {
        this.client = client;
        this.tourId = tourId;
    }

    public ClientUpdateRequest(Client client) {
        this.client = client;
    }
}

