package com.example.cryptotradesim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balanceUSD;

    public Account() {}

    public Account(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }

    public Long getId() {
        return id;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }

    public void setBalanceUSD(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }
}

