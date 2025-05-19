package com.example.cryptotradesim.dto;

public class BuyRequestDTO {
    private String symbol;
    private double amountUSD;

    public BuyRequestDTO() {}

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
