package com.example.solution_9_11.service;

import com.example.solution_9_11.ResourceNotFoundException;
import com.example.solution_9_11.dao.ClientDao;
import com.example.solution_9_11.dao.TourDao;
import com.example.solution_9_11.domain.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourDao tourDao;

    @Autowired
    public TourService(TourDao tourDao) {
        this.tourDao = tourDao;
    }

    @Transactional(readOnly = true)
    public Tour findTourById(long id) {
        Tour tour = tourDao.findTourById(id);

        if (tour == null) throw new ResourceNotFoundException("Tour not found");

        return tour;
    }

    @Transactional(readOnly = true)
    public List<Tour> listAll(Integer page, Integer limit, Integer offset) {
        return tourDao.listAll(page, limit, offset);
    }

    @Transactional(readOnly = true)
    public Long listCount() {
        return tourDao.listCount();
    }

    @Transactional
    public Tour createTour(String name) {
        Tour tour = new Tour();

        tour.setName(name);

        tourDao.save(tour);

        return tour;
    }

    @Transactional(readOnly = true)
    public Optional<Tour> findTourByName(String tourName) {
        return tourDao.findTourByName(tourName);
    }
}
