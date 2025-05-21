package com.example.cryptotradesim.model;

public class Holding {

    private Long id;

    private String symbol;
    private double amount;

    public Holding() {}

    public Holding(String symbol, double amount) {
        this.symbol = symbol;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public void addAmount(double delta) {
        this.amount += delta;
    }

    public void subtractAmount(double delta) {
        this.amount -= delta;
    }
}
