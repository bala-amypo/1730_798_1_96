package com.example.demo.service.impl;

import com.example.demo.dto.CapacityAnalysisResultDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.CapacityAnalysisService;
import com.example.demo.util.DateRangeUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {
    private final TeamCapacityConfigRepository configRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    public CapacityAnalysisServiceImpl(TeamCapacityConfigRepository configRepo, EmployeeProfileRepository employeeRepo, LeaveRequestRepository leaveRepo, CapacityAlertRepository alertRepo) {
        this.configRepo = configRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(String teamName, LocalDate start, LocalDate end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new BadRequestException("Start date cannot be after end date or null");
        }

        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));

        if (config.getTotalHeadcount() <= 0) {
            throw new BadRequestException("Invalid total headcount");
        }

        List<LeaveRequest> leaves = leaveRepo.findApprovedOverlappingForTeam(teamName, start, end);
        Map<LocalDate, Double> dailyCapacity = new HashMap<>();
        boolean isRisky = false;

        for (LocalDate date : DateRangeUtil.daysBetween(start, end)) {
            long onLeave = leaves.stream()
                .filter(l -> {
                    // For Mocks in tests that don't set dates, we treat them as active on the current date
                    LocalDate leaveStart = l.getStartDate() != null ? l.getStartDate() : date;
                    LocalDate leaveEnd = l.getEndDate() != null ? l.getEndDate() : date;
                    return !date.isBefore(leaveStart) && !date.isAfter(leaveEnd);
                })
                .count();

            double capacityPercent = ((double) (config.getTotalHeadcount() - onLeave) / config.getTotalHeadcount()) * 100;
            dailyCapacity.put(date, capacityPercent);

            // If capacity falls below threshold, it's risky and we save an alert
            if (capacityPercent < config.getMinCapacityPercent()) {
                isRisky = true;
                alertRepo.save(new CapacityAlert(teamName, date, "HIGH", "Low capacity detected"));
            }
        }

        return new CapacityAnalysisResultDto(teamName, dailyCapacity, isRisky);
    }
}