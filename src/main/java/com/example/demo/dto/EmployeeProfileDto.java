// package com.example.demo.dto;

import java.time.LocalDate;

public class EmployeeProfileDto {
    private Long id;
    private String employeeId;
    private String fullName;
    private String email;
    private String teamName;
    private String role;
    private boolean active = true;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

public class LeaveRequestDto {
    private Long id;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String reason;
    private String status = "PENDING";
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

public class CapacityAnalysisResultDto {
    private String teamName;
    private boolean risky;
    private java.util.Map<LocalDate, Double> capacityByDate;
    private java.util.List<CapacityAlertDto> alerts;
    
    // Getters and setters
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public boolean isRisky() { return risky; }
    public void setRisky(boolean risky) { this.risky = risky; }
    public java.util.Map<LocalDate, Double> getCapacityByDate() { return capacityByDate; }
    public void setCapacityByDate(java.util.Map<LocalDate, Double> capacityByDate) { this.capacityByDate = capacityByDate; }
    public java.util.List<CapacityAlertDto> getAlerts() { return alerts; }
    public void setAlerts(java.util.List<CapacityAlertDto> alerts) { this.alerts = alerts; }
}

public class CapacityAlertDto {
    private Long id;
    private String teamName;
    private LocalDate date;
    private String severity;
    private String message;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

public class AuthRequest {
    private String email;
    private String password;
    
    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;
    
    // Getters and setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}