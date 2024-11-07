package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorisRepository extends JpaRepository<Favoris, Long> {
}
