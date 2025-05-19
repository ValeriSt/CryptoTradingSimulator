package com.example.cryptotradesim.dto;

public class SellRequestDTO {
    private String symbol;
    private double amount;

    public SellRequestDTO() {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
