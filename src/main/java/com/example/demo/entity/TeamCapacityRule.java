package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "team_capacity_configs",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "teamName")
       })
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String teamName;

    @NotNull
    @Min(1)
    private Integer totalHeadcount;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer minCapacityPercent;

  
}
