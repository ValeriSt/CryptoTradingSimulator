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
        Transaction t = new Transaction();
        t.setId(rs.getLong("id"));
        t.setAction(rs.getString("action"));
        t.setSymbol(rs.getString("symbol"));
        t.setAmountCrypto(rs.getDouble("amount_crypto"));
        t.setPricePerUnit(rs.getDouble("price_per_unit"));
        t.setTotalUSD(rs.getDouble("total_usd"));
        t.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return t;
    };

    public void save(Transaction tx) {
        jdbcTemplate.update("""
            INSERT INTO transaction (action, symbol, amount_crypto, price_per_unit, total_usd, timestamp)
            VALUES (?, ?, ?, ?, ?, ?)
        """, tx.getAction(), tx.getSymbol(), tx.getAmountCrypto(), tx.getPricePerUnit(), tx.getTotalUSD(), Timestamp.valueOf(tx.getTimestamp()));
    }

    public List<Transaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM transaction ORDER BY timestamp DESC", rowMapper);
    }
}
