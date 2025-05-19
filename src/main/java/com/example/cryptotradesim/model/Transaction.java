package com.example.cryptotradesim.model;

import java.time.LocalDateTime;

public class Transaction {
    private String action;
    private String symbol;
    private double amountCrypto;
    private double pricePerUnit;
    private double totalUSD;
    private LocalDateTime timestamp;


    public  Transaction(String action, String symbol, double amountCrypto, double pricePerUnit, double totalUSD) {
        this.action = action;
        this.symbol = symbol;
        this.amountCrypto = amountCrypto;
        this.pricePerUnit = pricePerUnit;
        this.totalUSD = totalUSD;
        this.timestamp = LocalDateTime.now();
    }

    public String getAction() { return action; }
    public String getSymbol() { return symbol; }
    public double getAmountCrypto() { return amountCrypto; }
    public double getPricePerUnit() { return pricePerUnit; }
    public double getTotalUSD() { return totalUSD; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
