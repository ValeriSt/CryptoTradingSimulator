package com.example.cryptotradesim.model;

import java.time.LocalDateTime;

public class Transaction {

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
    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmountCrypto() {
        return amountCrypto;
    }
    public void setAmountCrypto(double amountCrypto) {
        this.amountCrypto = amountCrypto;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getTotalUSD() {
        return totalUSD;
    }
    public void setTotalUSD(double totalUSD) {
        this.totalUSD = totalUSD;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
