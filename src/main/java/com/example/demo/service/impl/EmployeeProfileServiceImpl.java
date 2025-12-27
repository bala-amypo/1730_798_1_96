package com.example.demo.service.impl;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {
    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile emp = new EmployeeProfile();
        emp.setEmployeeId(dto.getEmployeeId());
        emp.setFullName(dto.getFullName());
        emp.setEmail(dto.getEmail());
        emp.setTeamName(dto.getTeamName());
        emp.setRole(dto.getRole());
        return convertToDto(repo.save(emp));
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        existing.setFullName(dto.getFullName());
        existing.setTeamName(dto.getTeamName());
        existing.setRole(dto.getRole());
        return convertToDto(repo.save(existing));
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        existing.setActive(false);
        repo.save(existing);
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        return repo.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String team) {
        return repo.findByTeamNameAndActiveTrue(team).stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repo.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private EmployeeProfileDto convertToDto(EmployeeProfile emp) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(emp.getId());
        dto.setEmployeeId(emp.getEmployeeId());
        dto.setFullName(emp.getFullName());
        dto.setEmail(emp.getEmail());
        dto.setTeamName(emp.getTeamName());
        dto.setRole(emp.getRole());
        dto.setActive(emp.isActive());
        return dto;
    }
}