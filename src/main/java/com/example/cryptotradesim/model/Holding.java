package com.example.cryptotradesim.model;

public class Holding {
    private String symbol;
    private double amount;

    public Holding(String symbol, double amount) {
        this.symbol = symbol;
        this.amount = amount;
    }

    public String getSymbol() { return symbol; }
    public double getAmount() { return amount; }
    public void addAmount(double delta) { this.amount += delta; }
    public void subtractAmount(double delta) { this.amount -= delta; }
}
