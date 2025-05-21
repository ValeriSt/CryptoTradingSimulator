package com.example.cryptotradesim.model;


public class Account {

    private Long id;

    private double balanceUSD;

    public Account() {}

    public Account(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }

    public void setBalanceUSD(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }
}

