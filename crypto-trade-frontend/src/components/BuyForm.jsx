import React, { useState } from "react";

function BuyForm({ symbols, onBuySuccess }) {
  const [symbol, setSymobl] = useState("BTC");
  const [amountUSD, setAmountUSD] = useState("");

const handleBuy = async (e) => {
  e.preventDefault();
  const form = e.target;

  const selectedSymbol = form.elements.symbol.value;
  const amount = form.elements.amountUSD.value;

  if (!amount || isNaN(amount) || amount <= 0) {
      alert("Enter a valid USD amount");
      return;
    }

  const request = {
    symbol: selectedSymbol,
    amountUSD: parseFloat(amount),
  };

  try {
    const res = await fetch("http://localhost:5000/api/account/buy", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(request),
    });

    if (!res.ok) throw new Error("Buy failed");
    alert(await res.text());

    form.reset();
    onBuySuccess?.();
  } catch (err) {
    console.error("Buy failed:", err);
    alert("Error buying crypto.");
  }
};


  return (
 <form onSubmit={handleBuy}>
      <label>
        Symbol:
        <select name="symbol" defaultValue={symbols[0]}>
          {symbols.map((s) => (
            <option key={s} value={s}>{s}</option>
          ))}
        </select>
      </label>
      <input name="amountUSD" type="number" placeholder="Enter USD amount" />
      <button type="submit">Buy</button>
    </form>
  );
}

export default BuyForm;
