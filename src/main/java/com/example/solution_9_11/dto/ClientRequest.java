package com.example.solution_9_11.dto;

import com.example.solution_9_11.domain.Client;

public class ClientRequest {
    public Client client;
    public String tourName;

    public ClientRequest(Client client, String tourName) {
        this.client = client;
        this.tourName = tourName;
    }

    public ClientRequest(Client client) {
        this.client = client;
    }
}
