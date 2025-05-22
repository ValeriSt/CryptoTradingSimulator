import React, { useEffect, useState } from "react";
import "./Prices.css";

function Prices() {
  const [prices, setPrices] = useState({});
  const [previousPrices, setPreviousPrices] = useState({});

  useEffect(() => {
    const fetchPrices = async () => {
      try {
        const res = await fetch("http://localhost:5000/api/prices/live");
        const data = await res.json();
        setPrices((prevPrices) => {
          setPreviousPrices(prevPrices);
          return data;
        });
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
            {symbols.map((symbol) => {
              const current = prices[symbol];
              const previous = previousPrices[symbol];
              const diff = previous ? current - previous : 0;

              let className = "";
              if (diff > 0) className = "price-up";
              else if (diff < 0) className = "price-down";
              console.log(
                `${symbol} Prev: ${previous} Curr: ${current} Diff: ${diff}`
              );
              return (
                <tr key={symbol}>
                  <td>{symbol}</td>
                  <td className={className}>
                    ${current.toFixed(2)}
                    {diff !== 0 && (
                      <span className="price-arrow">
                        {diff > 0 ? " 🔼" : " 🔽"}
                      </span>
                    )}
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Prices;
