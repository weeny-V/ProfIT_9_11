package com.example.solution_9_11;

import com.example.solution_9_11.domain.Client;
import com.example.solution_9_11.dto.ClientRequest;
import com.example.solution_9_11.dto.ClientUpdateRequest;
import com.example.solution_9_11.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        clientRepository.deleteAll();
    }

    @Test
    public void TestGetClientByIdFound() throws Exception {
        Client client = Client.builder()
                .name("Vlad")
                .surname("Kruhlov")
                .birthDate(LocalDate.of(2000, 8, 16))
                .build();

        clientRepository.save(client);

        ResultActions response = mockMvc.perform(get("/api/client/{id}", client.getId()));

        response.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.data.name", is(client.getName())))
                .andExpect(jsonPath("$.data.surname", is(client.getSurname())))
                .andExpect(jsonPath("$.data.birthDate", is(client.getBirthDate().toString())));
    }

    @Test
    public void TestGetClientByIdNotFound() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/client/{id}", 0));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.statusCode", is(404)));
    }

    @Test
    public void TestCreateClient() throws Exception {
        Client client = Client.builder()
                .name("Vlad")
                .surname("Kruhlov")
                .birthDate(LocalDate.of(2000, 8, 16))
                .build();

        clientRepository.save(client);

        ClientRequest content = new ClientRequest(client);

        ResultActions response = mockMvc.perform(post("/api/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content)));

        response.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(client.getName())))
                .andExpect(jsonPath("$.surname", is(client.getSurname())))
                .andExpect(jsonPath("$.birthDate", is(client.getBirthDate().toString() )));
    }

    @Test
    public void TestUpdateClient() throws Exception {
        Client client = Client.builder()
                .name("Vlad")
                .surname("Kruhlov")
                .birthDate(LocalDate.of(2000, 8, 16))
                .build();

        clientRepository.save(client);

        ClientRequest content = new ClientRequest(client);

        ResultActions createResponse = mockMvc.perform(post("/api/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content)));

        createResponse.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(client.getName())))
                .andExpect(jsonPath("$.surname", is(client.getSurname())))
                .andExpect(jsonPath("$.birthDate", is(client.getBirthDate().toString() )));

        Client updatedClient = Client.builder()
                .id(client.getId())
                .name("Vlad_Updated")
                .surname("Kruhlov_Updated")
                .birthDate(client.getBirthDate())
                .build();

        ResultActions updateResponse = mockMvc.perform(put("/api/client/update/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ClientUpdateRequest(updatedClient))));

        updateResponse.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedClient.getName())))
                .andExpect(jsonPath("$.surname", is(updatedClient.getSurname())));
    }

    @Test
    public void TestDeleteClient() throws Exception {
        Client client = Client.builder()
                .name("Vlad")
                .surname("Kruhlov")
                .birthDate(LocalDate.of(2000, 8, 16))
                .build();

        clientRepository.save(client);

        ClientRequest content = new ClientRequest(client);

        ResultActions createResponse = mockMvc.perform(post("/api/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content)));

        createResponse.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(client.getName())))
                .andExpect(jsonPath("$.surname", is(client.getSurname())))
                .andExpect(jsonPath("$.birthDate", is(client.getBirthDate().toString() )));

        ResultActions deleteResponse = mockMvc.perform(delete("/api/client/delete/{id}", client.getId()));

        deleteResponse.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$", is("OK")));
    }
}
