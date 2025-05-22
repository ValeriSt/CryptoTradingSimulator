import React, { useEffect, useState } from "react";
import './SellForm.css'

function SellForm({ onSellSuccess }) {
  const [symbol, setSymbol] = useState("BTC");
  const [holdings, setHoldings] = useState([]);

  useEffect(() => {
    fetch("http://localhost:5000/api/account/holdings")
      .then((res) => res.json())
      .then(setHoldings)
      .catch((err) => console.error("Error fetching holdings:", err));
  }, []);

  const handleSell = async (e) => {
    e.preventDefault();

    const form = e.target;
    const symbol = form.symbol.value;
    const amountCrypto = parseFloat(form.amountCrypto.value);

    if (!symbol || isNaN(amountCrypto) || amountCrypto <= 0) {
      alert("Enter a valid symbol and crypto amount");
      return;
    }

    const res = await fetch("http://localhost:5000/api/account/sell", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ symbol, amountCrypto }),
    });

    const msg = await res.text();
    alert(msg);
    form.reset;
    onSellSuccess?.();
  };

  return (
    <form className="sell-form" onSubmit={handleSell}>
      <select name="symbol" required>
        <option value="">-- Select --</option>
        {holdings.map((h) => (
          <option key={h.symbol} value={h.symbol}>
            {h.symbol} ({h.amount.toFixed(6)})
          </option>
        ))}
      </select>
      <input
        name="amountCrypto"
        type="number"
        placeholder="Enter Crypto Amount"
        step="any"
        required
      />
      <button type="submit">Sell</button>
    </form>
  );
}

export default SellForm;
