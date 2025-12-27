package com.example.demo.controller;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/capacity")
public class CapacityController {
    private final CapacityAnalysisService capacityService;

    public CapacityController(CapacityAnalysisService capacityService) {
        this.capacityService = capacityService;
    }

    @GetMapping("/analyze")
    public CapacityAnalysisResultDto analyze(
            @RequestParam String teamName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return capacityService.analyzeTeamCapacity(teamName, start, end);
    }
}