package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaysRepository extends JpaRepository<Pays, Long> {
    Pays findByLibelle(String libelle);
}
