package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;

public class CapacityAnalysisResultDto {
    private String teamName;
    private Map<LocalDate, Double> capacityByDate;
    private boolean risky;

    public CapacityAnalysisResultDto(String teamName, Map<LocalDate, Double> capacityByDate, boolean risky) {
        this.teamName = teamName;
        this.capacityByDate = capacityByDate;
        this.risky = risky;
    }

    // Getters and Setters
    public String getTeamName() { return teamName; }
    public Map<LocalDate, Double> getCapacityByDate() { return capacityByDate; }
    public boolean isRisky() { return risky; }
}