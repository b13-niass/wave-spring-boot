package com.example.wavespringboot.data.repository;

import com.example.wavespringboot.data.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
