package com.example.cryptotradesim.dto;

public class SellRequestDTO {
    private String symbol;
    private double amountUSD;

    public SellRequestDTO() {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(double amountUSD) {
        this.amountUSD = amountUSD;
    }
}

