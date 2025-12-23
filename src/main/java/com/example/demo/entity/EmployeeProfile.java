package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String employeeId;
    private String fullName;
    private String email;
    private String teamName;
    private String role;
    private boolean active;
}
