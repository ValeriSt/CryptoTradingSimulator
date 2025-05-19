package com.example.cryptotradesim.dto;

public class AccountBalanceDTO {
    private double balanceUSD;

    public AccountBalanceDTO(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }
}
