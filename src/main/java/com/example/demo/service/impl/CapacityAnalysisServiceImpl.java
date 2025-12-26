package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAlertDto;
import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CapacityAlert;
import com.example.demo.model.LeaveRequest;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.CapacityAlertRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.util.DateRangeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {
    
    private final TeamCapacityConfigRepository capacityRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;
    
    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository capacityRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo) {
        this.capacityRepo = capacityRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }
    
    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(String teamName, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }
        
        TeamCapacityConfig config = capacityRepo.findByTeamName(teamName)
            .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found for team: " + teamName));
        
        if (config.getTotalHeadcount() <= 0) {
            throw new BadRequestException("Invalid total headcount: " + config.getTotalHeadcount());
        }
        
        List<LocalDate> dates = DateRangeUtil.daysBetween(startDate, endDate);
        List<LeaveRequest> leaves = leaveRepo.findApprovedOverlappingForTeam(teamName, startDate, endDate);
        
        Map<LocalDate, Double> capacityByDate = new HashMap<>();
        Map<LocalDate, Integer> leavesByDate = countLeavesByDate(leaves, dates);
        List<CapacityAlert> newAlerts = new ArrayList<>();
        
        boolean isRisky = false;
        
        for (LocalDate date : dates) {
            int onLeave = leavesByDate.getOrDefault(date, 0);
            int available = config.getTotalHeadcount() - onLeave;
            double capacityPercent = (double) available / config.getTotalHeadcount() * 100;
            capacityByDate.put(date, capacityPercent);
            
            if (capacityPercent < config.getMinCapacityPercent()) {
                isRisky = true;
                String severity = capacityPercent < 30 ? "HIGH" : capacityPercent < 50 ? "MEDIUM" : "LOW";
                String message = String.format("Team %s capacity at %.1f%% on %s (%d/%d available)",
                    teamName, capacityPercent, date, available, config.getTotalHeadcount());
                
                CapacityAlert alert = new CapacityAlert(teamName, date, severity, message);
                newAlerts.add(alert);
            }
        }
        
        if (!newAlerts.isEmpty()) {
            alertRepo.saveAll(newAlerts);
        }
        
        CapacityAnalysisResultDto result = new CapacityAnalysisResultDto();
        result.setTeamName(teamName);
        result.setRisky(isRisky);
        result.setCapacityByDate(capacityByDate);
        result.setAlerts(newAlerts.stream().map(this::toAlertDto).collect(Collectors.toList()));
        
        return result;
    }
    
    private Map<LocalDate, Integer> countLeavesByDate(List<LeaveRequest> leaves, List<LocalDate> dates) {
        Map<LocalDate, Integer> countMap = new HashMap<>();
        for (LocalDate date : dates) {
            countMap.put(date, 0);
        }
        
        for (LeaveRequest leave : leaves) {
            LocalDate current = leave.getStartDate();
            while (!current.isAfter(leave.getEndDate())) {
                if (countMap.containsKey(current)) {
                    countMap.put(current, countMap.get(current) + 1);
                }
                current = current.plusDays(1);
            }
        }
        
        return countMap;
    }
    
    private CapacityAlertDto toAlertDto(CapacityAlert alert) {
        CapacityAlertDto dto = new CapacityAlertDto();
        dto.setId(alert.getId());
        dto.setTeamName(alert.getTeamName());
        dto.setDate(alert.getDate());
        dto.setSeverity(alert.getSeverity());
        dto.setMessage(alert.getMessage());
        return dto;
    }
}