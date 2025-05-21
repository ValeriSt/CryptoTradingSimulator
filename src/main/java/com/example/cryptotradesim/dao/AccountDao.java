package com.example.cryptotradesim.dao;

import com.example.cryptotradesim.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Account> rowMapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setBalanceUSD(rs.getDouble("balance_usd"));
        return account;
    };

    public Account getMainAccount() {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM account ORDER BY id LIMIT 1", rowMapper
        );
    }

    public void updateBalance(double newBalance) {
        jdbcTemplate.update("UPDATE account SET balance_usd = ? WHERE id = (SELECT id FROM account ORDER BY id LIMIT 1)", newBalance);
    }

    public void insertInitialAccount(double initialBalance) {
        jdbcTemplate.update("INSERT INTO account (balance_usd) VALUES (?)", initialBalance);
    }

    public boolean isEmpty() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM account", Integer.class);
        return count == null || count == 0;
    }
}
