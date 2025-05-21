package com.example.cryptotradesim.controller;

import com.example.cryptotradesim.dto.BuyRequestDTO;
import com.example.cryptotradesim.dto.SellRequestDTO;
import com.example.cryptotradesim.model.Holding;
import com.example.cryptotradesim.model.Transaction;
import com.example.cryptotradesim.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/balance")
    public double getBalance() {
        return accountService.getBalance();
    }

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDTO request) {
        return accountService.buy(request);
    }

    @PostMapping("/sell")
    public String sell(@RequestBody SellRequestDTO request) {
        System.out.println(">>> SELL: " + request.getSymbol() + " $" + request.getAmountUSD());
        return accountService.sell(request);
    }

    @GetMapping("/holdings")
    public List<Holding> getHoldings() {
        return accountService.getHoldings();
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return accountService.getTransactions();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetAccount() {
        accountService.resetAccount();
        return ResponseEntity.ok("Account reset to $10,000 and all data cleared.");
    }

}
