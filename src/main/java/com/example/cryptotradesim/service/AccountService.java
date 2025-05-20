package com.example.cryptotradesim.service;

import com.example.cryptotradesim.dto.BuyRequestDTO;
import com.example.cryptotradesim.dto.SellRequestDTO;
import com.example.cryptotradesim.model.Account;
import com.example.cryptotradesim.model.Holding;
import com.example.cryptotradesim.model.Transaction;
import com.example.cryptotradesim.repository.AccountRepository;
import com.example.cryptotradesim.repository.HoldingRepository;
import com.example.cryptotradesim.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final HoldingRepository holdingRepository;
    private final TransactionRepository transactionRepository;

    private final Map<String, Double> mockPrices = Map.of(
            "BTC", 64000.0,
            "ETH", 3200.0,
            "SOL", 140.0
    );

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          HoldingRepository holdingRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.holdingRepository = holdingRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    public void init() {
        if (accountRepository.count() == 0) {
            accountRepository.save(new Account(10000.0));
        }
    }

    public double getBalance() {
        return accountRepository.findAll().get(0).getBalanceUSD();
    }

    public String buy(BuyRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountUSD = request.getAmountUSD();

        if (!mockPrices.containsKey(symbol)) {
            return "❌ Unsupported crypto: " + symbol;
        }

        Account account = accountRepository.findAll().get(0);
        if (account.getBalanceUSD() < amountUSD) {
            return "❌ Not enough balance.";
        }

        double price = mockPrices.get(symbol);
        double amountToBuy = amountUSD / price;

        account.setBalanceUSD(account.getBalanceUSD() - amountUSD);
        accountRepository.save(account);

        Holding holding = holdingRepository.findBySymbol(symbol).orElse(new Holding(symbol, 0));
        holding.addAmount(amountToBuy);
        holdingRepository.save(holding);

        transactionRepository.save(new Transaction("BUY", symbol, amountToBuy, price, amountUSD));

        return String.format("✅ Bought %.6f %s for $%.2f", amountToBuy, symbol, amountUSD);
    }

    public String sell(SellRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountUSD = request.getAmountUSD();

        if (!mockPrices.containsKey(symbol)) {
            return "❌ Unsupported crypto: " + symbol;
        }

        double price = mockPrices.get(symbol);
        if (price <= 0) return "❌ Invalid price for: " + symbol;

        double amountToSell = amountUSD / price;

        Optional<Holding> optional = holdingRepository.findBySymbol(symbol);
        if (optional.isEmpty() || optional.get().getAmount() < amountToSell) {
            return "❌ Not enough " + symbol + " to sell.";
        }

        Holding holding = optional.get();
        holding.subtractAmount(amountToSell);

        if (holding.getAmount() <= 0.000001) {
            holdingRepository.delete(holding);
        } else {
            holdingRepository.save(holding);
        }

        Account account = accountRepository.findAll().get(0);
        account.setBalanceUSD(account.getBalanceUSD() + amountUSD);
        accountRepository.save(account);

        transactionRepository.save(new Transaction("SELL", symbol, amountToSell, price, amountUSD));

        return String.format("✅ Sold %.6f %s for $%.2f", amountToSell, symbol, amountUSD);
    }


    public List<Holding> getHoldings() {
        return holdingRepository.findAll();
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
