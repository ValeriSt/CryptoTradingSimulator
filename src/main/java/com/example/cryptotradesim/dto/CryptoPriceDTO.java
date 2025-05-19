package com.example.cryptotradesim.dto;

public class CryptoPriceDTO {
    private String name;
    private String symbol;
    private double price;

    public CryptoPriceDTO(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }


}