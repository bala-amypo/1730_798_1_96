package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.service.CapacityAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName, LocalDate start, LocalDate end) {
        CapacityAnalysisResultDto dto = new CapacityAnalysisResultDto();
        dto.risky = false;
        dto.capacityByDate = new HashMap<>();
        return dto;
    }
}
