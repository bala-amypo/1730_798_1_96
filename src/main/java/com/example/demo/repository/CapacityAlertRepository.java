package com.example.demo.repository;

import com.example.demo.model.CapacityAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CapacityAlertRepository extends JpaRepository<CapacityAlert, Long> {
    @Query("SELECT a FROM CapacityAlert a WHERE a.teamName = :teamName " +
           "AND a.date BETWEEN :startDate AND :endDate")
    List<CapacityAlert> findByTeamNameAndDateBetween(
        @Param("teamName") String teamName,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
}