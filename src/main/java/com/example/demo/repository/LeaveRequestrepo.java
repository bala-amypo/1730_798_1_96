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

    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.teamName = :teamName AND l.status = 'APPROVED' " +
           "AND l.startDate <= :end AND l.endDate >= :start")
    List<LeaveRequest> findApprovedOverlappingForTeam(
            @Param("teamName") String teamName,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
