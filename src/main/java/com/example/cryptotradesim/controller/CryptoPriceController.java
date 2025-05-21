package com.example.cryptotradesim.controller;

import com.example.cryptotradesim.dto.CryptoPriceDTO;
import com.example.cryptotradesim.service.CryptoPriceWebSocketClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "*")
public class CryptoPriceController {

    private final CryptoPriceWebSocketClient priceClient;

    public CryptoPriceController(CryptoPriceWebSocketClient priceClient) {
        this.priceClient = priceClient;
    }

    @GetMapping("/live")
    public Map<String, Double> getLivePrices() {
        Map<String, Double> prices = priceClient.getLivePrices();
        System.out.println("Live prices map: " + prices);
        return prices;
    }

}

