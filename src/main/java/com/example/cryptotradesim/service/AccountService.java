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
    private final CryptoPriceWebSocketClient priceClient;

    @Autowired
    public AccountService(AccountDao accountDao,
                          HoldingDao holdingDao,
                          TransactionDao transactionDao,
                          CryptoPriceWebSocketClient priceClient) {
        this.accountDao = accountDao;
        this.holdingDao = holdingDao;
        this.transactionDao = transactionDao;
        this.priceClient = priceClient;
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

        Double price = priceClient.getLivePrices().get(symbol);
        if (price == null || price <= 0) {
            return "Price for " + symbol + " not available.";
        }

        Account account = accountDao.getMainAccount();
        if (account.getBalanceUSD() < amountUSD) {
            return " Not enough balance.";
        }

        double amountToBuy = amountUSD / price;

        // Deduct from balance
        account.setBalanceUSD(account.getBalanceUSD() - amountUSD);
        accountDao.updateBalance(account.getBalanceUSD());

        // Update holding
        Holding holding = holdingDao.findBySymbol(symbol)
                .orElse(new Holding(symbol, 0, 0));
        holding.addAmount(amountToBuy, amountUSD);
        holdingDao.save(holding);

        // Save transaction (no profit/loss for buy)
        transactionDao.save(new Transaction("BUY", symbol, amountToBuy, price, amountUSD));

        return String.format("Bought %.6f %s for $%.2f", amountToBuy, symbol, amountUSD);
    }

    public String sell(SellRequestDTO request) {
        String symbol = request.getSymbol().toUpperCase();
        double amountToSell = request.getAmountCrypto();

        Double price = priceClient.getLivePrices().get(symbol);

        if (price == null || price <= 0) {
            return "Price for " + symbol + " not available.";
        }

        double usdReceived = amountToSell * price;

        Optional<Holding> optional = holdingDao.findBySymbol(symbol);
        if (optional.isEmpty() || optional.get().getAmount() < amountToSell) {
            return "Not enough " + symbol + " to sell.";
        }

        Holding holding = optional.get();

        double originalBuyPrice = holding.getAverageBuyPrice();  // Calculate before modifying
        double profitOrLoss = Math.round((price - originalBuyPrice) * amountToSell * 100.0) / 100.0;

        if (profitOrLoss == -0.0) {
            profitOrLoss = 0.0;
        }

        holding.subtractAmount(amountToSell);
        if (holding.getAmount() <= 0.000001) {
            holdingDao.delete(holding);
        } else {
            // Reduce the totalCostUSD as well (proportional to amount sold)
            double costReduction = originalBuyPrice * amountToSell;
            holding.setTotalCostUSD(holding.getTotalCostUSD() - costReduction);
            holdingDao.save(holding);
        }

        Account account = accountDao.getMainAccount();
        account.setBalanceUSD(account.getBalanceUSD() + usdReceived);
        accountDao.updateBalance(account.getBalanceUSD());

        transactionDao.save(new Transaction(
                "SELL",
                symbol,
                amountToSell,
                price,
                usdReceived,
                profitOrLoss
        ));

        return String.format("Sold %.6f %s for $%.2f", amountToSell, symbol, usdReceived);
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
