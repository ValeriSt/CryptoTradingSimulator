import React, { useEffect, useState } from "react";
import './Prices.css'

function Prices() {
  const [prices, setPrices] = useState([]);

  useEffect(() => {
    fetch('http://localhost:5000/api/prices')
      .then((res) => res.json())
      .then((data) => setPrices(data))
      .catch((err) => console.error("Error fetching prices:", err));
  }, []);
  
  return (
  <div>
    <h2>Top Cryptos</h2>
    {prices.length === 0 ? (
      <p>No price data loaded yet.</p>
    ) : (
      <table className="prices-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Symbol</th>
            <th>Price (USD)</th>
          </tr>
        </thead>
        <tbody>
          {prices.map((coin) => (
            <tr key={coin.symbol}>
              <td>{coin.name}</td>
              <td>{coin.symbol}</td>
              <td>${coin.price.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    )}
  </div>
);

}

export default Prices;
