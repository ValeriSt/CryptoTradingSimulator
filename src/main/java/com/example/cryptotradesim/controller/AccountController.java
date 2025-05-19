package com.example.cryptotradesim.controller;


import com.example.cryptotradesim.dto.AccountBalanceDTO;
import com.example.cryptotradesim.dto.BuyRequestDTO;
import com.example.cryptotradesim.dto.SellRequestDTO;
import com.example.cryptotradesim.model.Holding;
import com.example.cryptotradesim.model.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
    private double balanceUSD = 10000.00;
    private final List<Transaction> transactionHistory = new ArrayList<>();

    private final Map<String, Double> mockPrices = Map.of(
            "BTC", 64000.0,
            "ETH", 3200.0,
            "SOL", 140.0
    );

    private final Map<String, Holding> holdings = new HashMap<>();

    @GetMapping("/balance")
    public AccountBalanceDTO getBalance() {
        return new AccountBalanceDTO(balanceUSD);
    }

    @PostMapping("/buy")
    public String buyCrypto(@RequestBody BuyRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountUSD = request.getAmountUSD();

        if (!mockPrices.containsKey(symbol)) {
            return "❌ Unsupported crypto: " + symbol;
        }

        double price = mockPrices.get(symbol);
        double amountToBuy = amountUSD / price;

        if (balanceUSD < amountUSD) {
            return "❌ Not enough balance. Current: $" + balanceUSD;
        }

        // Deduct balance
        balanceUSD -= amountUSD;

        // Add to holdings
        holdings.putIfAbsent(symbol, new Holding(symbol, 0.0));
        holdings.get(symbol).addAmount(amountToBuy);
        transactionHistory.add(new Transaction("BUY", symbol, amountToBuy, price, amountUSD));

        return String.format("✅ Bought %.6f %s for $%.2f", amountToBuy, symbol, amountUSD);
    }

    @GetMapping("/holdings")
    public List<Holding> getHoldings() {
        return new ArrayList<>(holdings.values());
    }

    @PostMapping("/sell")
    public String sellCrypto(@RequestBody SellRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountToSell = request.getAmount();

        if (!mockPrices.containsKey(symbol)) {
            return "❌ Unsupported crypto: " + symbol;
        }

        Holding holding = holdings.get(symbol);
        if (holding == null || holding.getAmount() < amountToSell) {
            return "❌ Not enough " + symbol + " to sell.";
        }

        double price = mockPrices.get(symbol);
        double usdEarned = amountToSell * price;

        // Update balance and holdings
        balanceUSD += usdEarned;
        holding.subtractAmount(amountToSell);
        transactionHistory.add(new Transaction("SELL", symbol, amountToSell, price, usdEarned));

        // Remove from holdings if now 0
        if (holding.getAmount() <= 0.000001) {
            holdings.remove(symbol);
        }

        return String.format("✅ Sold %.6f %s for $%.2f", amountToSell, symbol, usdEarned);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return transactionHistory;
    }
}
