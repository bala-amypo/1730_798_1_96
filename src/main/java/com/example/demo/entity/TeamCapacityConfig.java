package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeamCapacityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String teamName;
    private int totalHeadcount;
    private int minCapacityPercent;
}
