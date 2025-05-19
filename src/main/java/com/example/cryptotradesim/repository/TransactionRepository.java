package com.example.cryptotradesim.repository;

import com.example.cryptotradesim.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
