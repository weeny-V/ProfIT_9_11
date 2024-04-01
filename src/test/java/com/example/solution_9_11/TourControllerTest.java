package com.example.solution_9_11;

import com.example.solution_9_11.domain.Client;
import com.example.solution_9_11.domain.Tour;
import com.example.solution_9_11.repository.TourRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class TourControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        tourRepository.deleteAll();
    }

    @Test
    public void TestGetOneTourByIdNotFound() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/tour/{id}", 0));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.statusCode", is(404)));
    }

    @Test
    public void TestGetOneTourByIdFound() throws Exception {
        Tour tour = Tour.builder()
                .name("Kyiv-Krakow")
                .build();

        tourRepository.save(tour);

        ResultActions response = mockMvc.perform(get("/api/tour/{id}", tour.getId()));

        response.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(tour.getName())));
    }

    @Test
    public void TestCreateNewTour() throws Exception {
        Tour tour = Tour.builder()
                .name("Kyiv-Kharkiv")
                .build();

        ResultActions response = mockMvc.perform(post("/api/tour/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tour)));

        response.andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is(tour.getName())));
    }

    @Test
    public void TestGetTourList() throws Exception {
        List<Tour> tourList = new ArrayList<>();
        List<Client> clientList = new ArrayList<>();

        clientList.add(Client.builder()
                .name("123")
                .surname("123123")
                .birthDate(LocalDate.of(2000, 8, 16))
                .build());
        clientList.add(Client.builder()
                .name("321312")
                .surname("321321")
                .birthDate(LocalDate.of(2000, 2, 18))
                .build());
        tourList.add(
                Tour.builder()
                        .name("Kyiv-Lviv")
                        .clients(clientList)
                        .build());
        tourList.add(Tour.builder().name("Kyiv-Kharkiv").build());

        tourRepository.saveAll(tourList);

        ResultActions response = mockMvc.perform(get("/api/tour/all"));

        response.andDo(print())
                .andExpect(jsonPath("$.count", is(tourList.size())));
    }
}
