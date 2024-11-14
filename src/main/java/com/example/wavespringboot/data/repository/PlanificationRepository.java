package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.Planification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface PlanificationRepository extends JpaRepository<Planification, Long> {
    @Query("SELECT p FROM Planification p WHERE p.recurrenceType = 'DAILY' OR p.recurrenceType = 'WEEKLY' OR p.recurrenceType = 'MONTHLY'")
    List<Planification> findAllRecurringPlanifications();

    @Query("SELECT p FROM Planification p WHERE p.sender.id = :userId")
    List<Planification> findBySenderId(@Param("userId") Long userId);
}

