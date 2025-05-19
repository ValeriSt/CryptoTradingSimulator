package com.example.cryptotradesim.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_log")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String symbol;
    private double amountCrypto;
    private double pricePerUnit;
    private double totalUSD;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String action, String symbol, double amountCrypto, double pricePerUnit, double totalUSD) {
        this.action = action;
        this.symbol = symbol;
        this.amountCrypto = amountCrypto;
        this.pricePerUnit = pricePerUnit;
        this.totalUSD = totalUSD;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getAmountCrypto() {
        return amountCrypto;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getTotalUSD() {
        return totalUSD;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
