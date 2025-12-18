package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee_profiles",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "employeeId"),
           @UniqueConstraint(columnNames = "email")
       })
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "employeeId", nullable = false, unique = true)
    private String employeeId;

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    private String teamName;

    @NotBlank
    private String role;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "employee_colleagues",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "colleague_id")
    )
    private Set<EmployeeProfile> colleagues = new HashSet<>();

    // Default constructor
    public EmployeeProfile() {
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    // All-args constructor
    public EmployeeProfile(String employeeId, String fullName, String email, String teamName, String role) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.teamName = teamName;
        this.role = role;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<EmployeeProfile> getColleagues() {
        return colleagues;
    }

    public void setColleagues(Set<EmployeeProfile> colleagues) {
        this.colleagues = colleagues;
    }

} 
