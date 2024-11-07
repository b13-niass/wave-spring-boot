package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.Planification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanificationRepository extends JpaRepository<Planification, Long> {
}
