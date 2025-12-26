package com.example.demo.controller;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/capacity")
public class CapacityAnalysisController {
    
    private final CapacityAnalysisService capacityService;
    
    public CapacityAnalysisController(CapacityAnalysisService capacityService) {
        this.capacityService = capacityService;
    }
    
    @GetMapping("/analyze/{teamName}")
    public ResponseEntity<CapacityAnalysisResultDto> analyzeTeamCapacity(
            @PathVariable String teamName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(capacityService.analyzeTeamCapacity(teamName, startDate, endDate));
    }
}