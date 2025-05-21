import React, { useEffect, useState } from "react";
import "./Prices.css";

function Prices() {
  const [prices, setPrices] = useState({});

  useEffect(() => {
    const fetchPrices = async () => {
      try {
        const res = await fetch("http://localhost:5000/api/prices/live");
        const data = await res.json();
        setPrices(data);
      } catch (err) {
        console.error("Error fetching live prices:", err);
      }
    };

    fetchPrices();

    const interval = setInterval(fetchPrices, 5000);

    return () => clearInterval(interval);
  }, []);

  const symbols = Object.keys(prices);

  return (
    <div>
      <h2>📈 Live Crypto Prices</h2>
      {symbols.length === 0 ? (
        <p>⏳ Loading live prices...</p>
      ) : (
        <table className="prices-table">
          <thead>
            <tr>
              <th>Symbol</th>
              <th>Price (USDT)</th>
            </tr>
          </thead>
          <tbody>
            {symbols.map((symbol) => (
              <tr key={symbol}>
                <td>{symbol}</td>
                <td>${prices[symbol].toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Prices;
