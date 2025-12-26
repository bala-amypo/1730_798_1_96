package com.example.demo.service.impl;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.LeaveRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {
    
    private final LeaveRequestRepository leaveRepo;
    private final EmployeeProfileRepository employeeRepo;
    
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo, EmployeeProfileRepository employeeRepo) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }
    
    @Override
    public LeaveRequestDto create(LeaveRequestDto dto) {
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }
        
        EmployeeProfile employee = employeeRepo.findById(dto.getEmployeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        
        LeaveRequest entity = new LeaveRequest();
        entity.setEmployee(employee);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setType(dto.getType());
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus());
        
        LeaveRequest saved = leaveRepo.save(entity);
        return toDto(saved);
    }
    
    @Override
    public LeaveRequestDto approve(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
        entity.setStatus("APPROVED");
        LeaveRequest saved = leaveRepo.save(entity);
        return toDto(saved);
    }
    
    @Override
    public LeaveRequestDto reject(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
        entity.setStatus("REJECTED");
        LeaveRequest saved = leaveRepo.save(entity);
        return toDto(saved);
    }
    
    @Override
    public List<LeaveRequestDto> getByEmployee(Long employeeId) {
        EmployeeProfile employee = employeeRepo.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        
        return leaveRepo.findByEmployee(employee).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<LeaveRequestDto> getOverlappingForTeam(String teamName, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }
        
        return leaveRepo.findApprovedOverlappingForTeam(teamName, startDate, endDate).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    private LeaveRequestDto toDto(LeaveRequest entity) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setType(entity.getType());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}