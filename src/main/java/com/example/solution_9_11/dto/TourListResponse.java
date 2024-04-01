package com.example.solution_9_11.dto;

import com.example.solution_9_11.domain.Tour;

import java.util.List;

public class TourListResponse {
    public int code;
    public Long count;
    public List<Tour> tours;

    public TourListResponse(int code, Long count, List<Tour> tours) {
        this.code = code;
        this.count = count;
        this.tours = tours;
    }
}
