package com.example.demo.repository;

import com.example.demo.model.LeaveRequest;
import com.example.demo.model.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(EmployeeProfile employee);

    @Query("SELECT l FROM LeaveRequest l WHERE l.employee.teamName = :teamName " +
           "AND l.status = 'APPROVED' AND l.startDate <= :endDate AND l.endDate >= :startDate")
    List<LeaveRequest> findApprovedOverlappingForTeam(@Param("teamName") String teamName, 
                                                      @Param("startDate") LocalDate startDate, 
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM LeaveRequest l WHERE l.status = 'APPROVED' AND :date BETWEEN l.startDate AND l.endDate")
    List<LeaveRequest> findApprovedOnDate(@Param("date") LocalDate date);
}