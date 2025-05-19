package com.example.cryptotradesim.repository;

import com.example.cryptotradesim.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {
    Optional<Holding> findBySymbol(String symbol);
}
