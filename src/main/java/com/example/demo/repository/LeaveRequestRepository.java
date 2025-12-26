package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Find overlapping approved leaves for a team
    @Query(
        SELECT lr FROM LeaveRequest lr
        WHERE lr.employee.teamName = :teamName
          AND lr.status = 'APPROVED'
          AND lr.startDate <= :endDate
          AND lr.endDate >= :startDate
    )
    List<LeaveRequest> findApprovedOverlappingForTeam(
            @Param("teamName") String teamName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Find all leave requests for a specific employee
    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

}
