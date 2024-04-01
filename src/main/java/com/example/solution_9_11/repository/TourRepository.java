package com.example.solution_9_11.repository;

import com.example.solution_9_11.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<Tour> findTourById(Long id);
}
