package com.example.cryptotradesim.controller;

import com.example.cryptotradesim.dto.CryptoPriceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "*")
public class CryptoPriceController {

    @GetMapping
    public List<CryptoPriceDTO> getMockPrices() {
        return List.of(
                new CryptoPriceDTO("Bitcoin", "BTC", 64325.0),
                new CryptoPriceDTO("Ethereum", "ETH", 3182.5),
                new CryptoPriceDTO("Solana", "SOL", 142.0)
        );
    }
}
