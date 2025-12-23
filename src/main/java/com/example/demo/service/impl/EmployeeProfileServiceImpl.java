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

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile e = new EmployeeProfile();
        e.setEmployeeId(dto.employeeId);
        e.setFullName(dto.fullName);
        e.setEmail(dto.email);
        e.setTeamName(dto.teamName);
        e.setRole(dto.role);
        repository.save(e);
        dto.id = e.getId();
        return dto;
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        e.setFullName(dto.fullName);
        e.setTeamName(dto.teamName);
        e.setRole(dto.role);
        repository.save(e);
        return dto;
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        e.setActive(false);
        repository.save(e);
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.id = e.getId();
        dto.fullName = e.getFullName();
        dto.email = e.getEmail();
        dto.teamName = e.getTeamName();
        dto.role = e.getRole();
        return dto;
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repository.findByTeamNameAndActiveTrue(teamName)
                .stream().map(e -> {
                    EmployeeProfileDto d = new EmployeeProfileDto();
                    d.id = e.getId();
                    d.fullName = e.getFullName();
                    return d;
                }).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repository.findAll()
                .stream().map(e -> {
                    EmployeeProfileDto d = new EmployeeProfileDto();
                    d.id = e.getId();
                    d.fullName = e.getFullName();
                    return d;
                }).collect(Collectors.toList());
    }
}
