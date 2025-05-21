package com.example.cryptotradesim.dao;

import com.example.cryptotradesim.model.Holding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HoldingDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HoldingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Holding> rowMapper = (rs, rowNum) -> {
        Holding h = new Holding();
        h.setId(rs.getLong("id"));
        h.setSymbol(rs.getString("symbol"));
        h.setAmount(rs.getDouble("amount"));
        return h;
    };

    public Optional<Holding> findBySymbol(String symbol) {
        List<Holding> list = jdbcTemplate.query(
                "SELECT * FROM holding WHERE symbol = ?", rowMapper, symbol);
        return list.stream().findFirst();
    }

    public void save(Holding holding) {
        Optional<Holding> existing = findBySymbol(holding.getSymbol());
        if (existing.isPresent()) {
            jdbcTemplate.update(
                    "UPDATE holding SET amount = ? WHERE symbol = ?",
                    holding.getAmount(), holding.getSymbol()
            );
        } else {
            jdbcTemplate.update(
                    "INSERT INTO holding (symbol, amount) VALUES (?, ?)",
                    holding.getSymbol(), holding.getAmount()
            );
        }
    }

    public void delete(Holding holding) {
        jdbcTemplate.update("DELETE FROM holding WHERE symbol = ?", holding.getSymbol());
    }

    public List<Holding> findAll() {
        return jdbcTemplate.query("SELECT * FROM holding", rowMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM holding");
    }

}
