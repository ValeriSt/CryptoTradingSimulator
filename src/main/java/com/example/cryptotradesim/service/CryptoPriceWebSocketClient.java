package com.example.cryptotradesim.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CryptoPriceWebSocketClient extends WebSocketClient {

    private final Map<String, Double> livePrices = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, String> symbolMap = Map.ofEntries(
            Map.entry("XBT/USDT", "BTC"),
            Map.entry("ETH/USDT", "ETH"),
            Map.entry("SOL/USDT", "SOL"),
            Map.entry("ADA/USDT", "ADA"),
            Map.entry("DOGE/USDT", "DOGE"),
            Map.entry("XRP/USDT", "XRP"),
            Map.entry("DOT/USDT", "DOT"),
            Map.entry("AVAX/USDT", "AVAX"),
            Map.entry("MATIC/USDT", "MATIC"),
            Map.entry("LINK/USDT", "LINK"),
            Map.entry("TRX/USDT", "TRX"),
            Map.entry("BCH/USDT", "BCH"),
            Map.entry("LTC/USDT", "LTC"),
            Map.entry("ATOM/USDT", "ATOM"),
            Map.entry("XLM/USDT", "XLM"),
            Map.entry("UNI/USDT", "UNI"),
            Map.entry("XMR/USDT", "XMR"),
            Map.entry("NEAR/USDT", "NEAR"),
            Map.entry("FIL/USDT", "FIL"),
            Map.entry("ICP/USDT", "ICP")
    );


    public CryptoPriceWebSocketClient() throws Exception {
        super(new URI("wss://ws.kraken.com/"));
        this.connectBlocking();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to Kraken WebSocket");

        send("""
            {
                "event": "subscribe",
                "pair": [
                    "XBT/USDT", "ETH/USDT", "SOL/USDT", "ADA/USDT", "DOGE/USDT",
                    "XRP/USDT", "DOT/USDT", "AVAX/USDT", "MATIC/USDT", "LINK/USDT",
                    "TRX/USDT", "BCH/USDT", "LTC/USDT", "ATOM/USDT", "XLM/USDT",
                    "UNI/USDT", "XMR/USDT", "NEAR/USDT", "FIL/USDT", "ICP/USDT"
                ],
                "subscription": {
                    "name": "ticker"
                }
            }
        """);
    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("[")) {
            try {
                JSONArray array = new JSONArray(message);

                if (array.length() < 4) return;

                JSONObject ticker = array.getJSONObject(1);
                String pair = array.getString(3);

                if (ticker.has("c")) {
                    JSONArray close = ticker.getJSONArray("c");
                    double price = close.getDouble(0);

                    String symbol = symbolMap.getOrDefault(pair, pair);
                    livePrices.put(symbol, price);
                    System.out.println("Updated: " + symbol + " = " + price);
                }

            } catch (Exception e) {
                System.err.println("Failed to parse message: " + e.getMessage());
            }
        }
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
    }

    public Map<String, Double> getLivePrices() {
        return livePrices;
    }
}
