package com.example.solution_9_11.controller;

import com.example.solution_9_11.ResponsesWithData;
import com.example.solution_9_11.domain.Tour;
import com.example.solution_9_11.dto.TourListResponse;
import com.example.solution_9_11.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour")
public class TourController {
    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/{id}")
    Tour findTour(@PathVariable String id) {
        return tourService.findTourById(Long.parseLong(id));
    }

    @GetMapping("/all")
    ResponseEntity<TourListResponse> findAll(
            @Nullable @RequestParam Integer page,
            @Nullable @RequestParam Integer limit,
            @Nullable @RequestParam Integer offset
    ) {
        return ResponseEntity.ok(new TourListResponse(HttpStatus.OK.value(), tourService.listCount(), tourService.listAll(page, limit, offset)));
    }

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody Tour tour) {
        return ResponseEntity.ok(new ResponsesWithData(HttpStatus.OK.value(), "SUCCESSFULLY CREATED", tourService.createTour(tour.getName())));
    }
}
