package com.example.cryptotradesim.model;

public class Holding {

    private Long id;

    private String symbol;
    private double amount;
    private double totalCostUSD;

    public Holding() {}

    public Holding(String symbol, double amount, double totalCostUSD) {
        this.symbol = symbol;
        this.amount = amount;
        this.totalCostUSD = totalCostUSD;
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

    public double getTotalCostUSD() {
        return totalCostUSD;
    }
    public void setTotalCostUSD(double totalCostUSD) {
        this.totalCostUSD = totalCostUSD;
    }

    public void addAmount(double amountToAdd, double usdCost) {
        this.totalCostUSD += usdCost;
        this.amount += amountToAdd;
    }

    public void subtractAmount(double delta) {
        this.amount -= delta;
    }

    public double getAverageBuyPrice() {
        return amount == 0 ? 0 : totalCostUSD / amount;
    }
}
