package com.example.solution_9_11.service;

import com.example.solution_9_11.ResourceNotFoundException;
import com.example.solution_9_11.dao.ClientDao;
import com.example.solution_9_11.dao.TourDao;
import com.example.solution_9_11.domain.Client;
import com.example.solution_9_11.domain.Tour;
import com.example.solution_9_11.dto.ClientUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientDao clientDao;
    private final TourDao tourDao;

    @Autowired
    public ClientService(ClientDao clientDao, TourDao tourDao) {
        this.clientDao = clientDao;
        this.tourDao = tourDao;
    }

    @Transactional(readOnly = true)
    public Client findClientById(long id) {
        Client client = clientDao.findClientById(id);

        if (client == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return client;
    }

    @Transactional
    public Client createClient(String name, String surname, LocalDate birthDate, String tourName) {
        Client client = new Client(name, surname, birthDate);

        if (tourName != null) {
            tourDao.findTourByName(tourName).ifPresentOrElse(client::setTour, () -> {
                Tour tour = new Tour(tourName);

                client.setTour(tour);
                tour.getClients().add(client);

                tourDao.save(tour);
            });
        }

        clientDao.save(client);

        return client;
    }

    @Transactional
    public Client updateClient(Long id, ClientUpdateRequest data) {
        if (clientDao.isClientExist(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        Client client = data.client;

        client.setId(id);

        if (data.tourId != null) {
            Tour tour = tourDao.findTourById(Long.parseLong(data.tourId));

            if (tour == null) {
                throw new ResourceNotFoundException("Tour not found");
            }

            client.setTour(tour);
        }

        return clientDao.update(client);
    }

    @Transactional
    public String deleteClient(long id) {
        Client client = clientDao.findClientById(id);

        if (client == null) throw new ResourceNotFoundException("User not found");

        clientDao.delete(client);

        return "OK";
    }

    @Transactional(readOnly = true)
    public List<Client> listAll(Integer page, Integer limit, Integer offset) {
        return clientDao.listAll(page, limit, offset);
    }
}
