import React, { useEffect, useState } from "react";

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
    const selectedSymbol = form.elements.symbol.value;
    const amount = parseFloat(form.elements.amountUSD.value);

    if (!selectedSymbol || isNaN(amount) || amount <= 0) {
      alert("Enter a valid symbol and USD amount");
      return;
    }

    const request = {
      symbol: selectedSymbol,
      amountUSD: amount,
    };

    try {
      const res = await fetch("http://localhost:5000/api/account/sell", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request),
      });

      const text = await res.text();

      if (!res.ok) throw new Error(text);
      alert(text);
      form.reset();
      if (onSellSuccess) onSellSuccess();
    } catch (err) {
      console.error("Fetch failed:", err.message);
      alert("Sell failed: " + err.message);
    }
  };

  return (
    <form onSubmit={handleSell}>
      <select name="symbol">
        <option value="">-- Select --</option>
        {holdings.map((h) => (
          <option key={h.symbol} value={h.symbol}>
            {h.symbol} ({h.amount.toFixed(6)})
          </option>
        ))}
      </select>

      <input name="amountUSD" type="number" placeholder="Enter USD" />

      <button type="submit">Sell</button>
    </form>
  );
}

export default SellForm;
