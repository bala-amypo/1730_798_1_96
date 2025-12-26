package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

public class CapacityAnalysisResultDto {
    private String teamName;
    private boolean risky;
    private Map<LocalDate, Double> capacityByDate;
    private List<CapacityAlertDto> alerts;
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public boolean isRisky() {
        return risky;
    }
    
    public void setRisky(boolean risky) {
        this.risky = risky;
    }
    
    public Map<LocalDate, Double> getCapacityByDate() {
        return capacityByDate;
    }
    
    public void setCapacityByDate(Map<LocalDate, Double> capacityByDate) {
        this.capacityByDate = capacityByDate;
    }
    
    public List<CapacityAlertDto> getAlerts() {
        return alerts;
    }
    
    public void setAlerts(List<CapacityAlertDto> alerts) {
        this.alerts = alerts;
    }
}