package com.example.cryptotradesim.repository;

import com.example.cryptotradesim.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
