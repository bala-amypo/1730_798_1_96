package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeProfileServiceImpl implements EmployeeProfileService {
    
    private final EmployeeProfileRepository repository;
    
    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile entity = new EmployeeProfile();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setTeamName(dto.getTeamName());
        entity.setRole(dto.getRole());
        entity.setActive(dto.isActive());
        
        EmployeeProfile saved = repository.save(entity);
        return toDto(saved);
    }
    
    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        if (dto.getFullName() != null) entity.setFullName(dto.getFullName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getTeamName() != null) entity.setTeamName(dto.getTeamName());
        if (dto.getRole() != null) entity.setRole(dto.getRole());
        
        EmployeeProfile saved = repository.save(entity);
        return toDto(saved);
    }
    
    @Override
    public void deactivate(Long id) {
        EmployeeProfile entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        entity.setActive(false);
        repository.save(entity);
    }
    
    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return toDto(entity);
    }
    
    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repository.findByTeamNameAndActiveTrue(teamName).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EmployeeProfileDto> getAll() {
        return repository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    private EmployeeProfileDto toDto(EmployeeProfile entity) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setTeamName(entity.getTeamName());
        dto.setRole(entity.getRole());
        dto.setActive(entity.isActive());
        return dto;
    }
}