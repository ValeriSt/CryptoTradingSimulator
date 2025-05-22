package com.example.cryptotradesim.dao;

import com.example.cryptotradesim.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Transaction> rowMapper = (rs, rowNum) -> {
        Transaction tx = new Transaction();
        tx.setId(rs.getLong("id"));
        tx.setAction(rs.getString("action"));
        tx.setSymbol(rs.getString("symbol"));
        tx.setAmountCrypto(rs.getDouble("amount_crypto"));
        tx.setPricePerUnit(rs.getDouble("price_per_unit"));
        tx.setTotalUSD(rs.getDouble("total_usd"));
        tx.setProfitOrLoss(rs.getObject("profit_or_loss", Double.class));
        tx.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return tx;
    };

    public void save(Transaction tx) {
        if (tx.getProfitOrLoss() == null) {
            jdbcTemplate.update(
                    "INSERT INTO transaction (action, symbol, amount_crypto, price_per_unit, total_usd, timestamp) VALUES (?, ?, ?, ?, ?, ?)",
                    tx.getAction(), tx.getSymbol(), tx.getAmountCrypto(),
                    tx.getPricePerUnit(), tx.getTotalUSD(), tx.getTimestamp()
            );
        } else {
            jdbcTemplate.update(
                    "INSERT INTO transaction (action, symbol, amount_crypto, price_per_unit, total_usd, profit_or_loss, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    tx.getAction(), tx.getSymbol(), tx.getAmountCrypto(),
                    tx.getPricePerUnit(), tx.getTotalUSD(), tx.getProfitOrLoss(), tx.getTimestamp()
            );
        }
    }

    public List<Transaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM transaction ORDER BY timestamp DESC", rowMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM transaction");
    }
}
