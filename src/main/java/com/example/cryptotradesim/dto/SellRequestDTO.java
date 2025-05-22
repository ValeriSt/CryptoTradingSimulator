package com.example.cryptotradesim.dto;

public class SellRequestDTO {
    private String symbol;
    private double amountCrypto;

    public SellRequestDTO() {}

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
}

