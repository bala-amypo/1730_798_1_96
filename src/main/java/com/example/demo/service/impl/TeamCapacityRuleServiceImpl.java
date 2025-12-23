package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TeamCapacityConfig;
import com.example.demo.repository.TeamCapacityConfigRepository;
import com.example.demo.service.TeamCapacityRuleService;
import org.springframework.stereotype.Service;

@Service
public class TeamCapacityRuleServiceImpl implements TeamCapacityRuleService {

    private final TeamCapacityConfigRepository repository;

    public TeamCapacityRuleServiceImpl(TeamCapacityConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamCapacityConfig createRule(TeamCapacityConfig rule) {
        if (rule.getTotalHeadcount() < 1) {
            throw new BadRequestException("Invalid total headcount");
        }
        return repository.save(rule);
    }

    @Override
    public TeamCapacityConfig updateRule(Long id, TeamCapacityConfig rule) {
        TeamCapacityConfig existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));
        existing.setTotalHeadcount(rule.getTotalHeadcount());
        existing.setMinCapacityPercent(rule.getMinCapacityPercent());
        return repository.save(existing);
    }

    @Override
    public TeamCapacityConfig getRuleByTeam(String teamName) {
        return repository.findByTeamName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Capacity config not found"));
    }
}
