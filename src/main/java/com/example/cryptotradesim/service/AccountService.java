package com.example.cryptotradesim.service;

import com.example.cryptotradesim.dao.AccountDao;
import com.example.cryptotradesim.dao.HoldingDao;
import com.example.cryptotradesim.dao.TransactionDao;
import com.example.cryptotradesim.dto.BuyRequestDTO;
import com.example.cryptotradesim.dto.SellRequestDTO;
import com.example.cryptotradesim.model.Account;
import com.example.cryptotradesim.model.Holding;
import com.example.cryptotradesim.model.Transaction;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountDao accountDao;
    private final HoldingDao holdingDao;
    private final TransactionDao transactionDao;

    private final Map<String, Double> mockPrices = Map.of(
            "BTC", 64000.0,
            "ETH", 3200.0,
            "SOL", 140.0
    );

    @Autowired
    public AccountService(AccountDao accountDao,
                          HoldingDao holdingDao,
                          TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.holdingDao = holdingDao;
        this.transactionDao = transactionDao;
    }

    @PostConstruct
    public void init() {
        if (accountDao.isEmpty()) {
            accountDao.insertInitialAccount(10000.0);
        }
    }

    public double getBalance() {
        return accountDao.getMainAccount().getBalanceUSD();
    }

    public String buy(BuyRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountUSD = request.getAmountUSD();

        if (!mockPrices.containsKey(symbol)) {
            return "Unsupported crypto: " + symbol;
        }

        Account account = accountDao.getMainAccount();
        if (account.getBalanceUSD() < amountUSD) {
            return "Not enough balance.";
        }

        double price = mockPrices.get(symbol);
        double amountToBuy = amountUSD / price;

        account.setBalanceUSD(account.getBalanceUSD() - amountUSD);
        accountDao.updateBalance(account.getBalanceUSD());

        Holding holding = holdingDao.findBySymbol(symbol).orElse(new Holding(symbol, 0));
        holding.addAmount(amountToBuy);
        holdingDao.save(holding);

        transactionDao.save(new Transaction("BUY", symbol, amountToBuy, price, amountUSD));

        return String.format("Bought %.6f %s for $%.2f", amountToBuy, symbol, amountUSD);
    }

    public String sell(SellRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountUSD = request.getAmountUSD();

        if (!mockPrices.containsKey(symbol)) {
            return "Unsupported crypto: " + symbol;
        }

        double price = mockPrices.get(symbol);
        if (price <= 0) return "nvalid price for: " + symbol;

        double amountToSell = amountUSD / price;

        Optional<Holding> optional = holdingDao.findBySymbol(symbol);
        if (optional.isEmpty() || optional.get().getAmount() < amountToSell) {
            return "Not enough " + symbol + " to sell.";
        }

        Holding holding = optional.get();
        holding.subtractAmount(amountToSell);

        if (holding.getAmount() <= 0.000001) {
            holdingDao.delete(holding);
        } else {
            holdingDao.save(holding);
        }

        Account account = accountDao.getMainAccount();
        account.setBalanceUSD(account.getBalanceUSD() + amountUSD);
        accountDao.updateBalance(account.getBalanceUSD());

        transactionDao.save(new Transaction("SELL", symbol, amountToSell, price, amountUSD));

        return String.format("Sold %.6f %s for $%.2f", amountToSell, symbol, amountUSD);
    }

    public List<Holding> getHoldings() {
        return holdingDao.findAll();
    }

    public List<Transaction> getTransactions() {
        return transactionDao.findAll();
    }

    public void resetAccount() {
        accountDao.updateBalance(10000.0);
        holdingDao.deleteAll();
        transactionDao.deleteAll();
    }
}
