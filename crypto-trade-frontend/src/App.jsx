import "./index.css";
import AccountInfo from "./components/AccountInfo";
import Prices from "./components/Prices";
import BuyForm from "./components/BuyForm";
import SellForm from "./components/SellForm";
import TransactionHistory from "./components/TransactionHistory";
import ResetButton from "./components/ResetButton";
import { useState, useEffect } from "react";

function App() {
  const [refreshKey, setRefreshKey] = useState(0);
  const [symbols, setSymbols] = useState([]);

  const triggerRefresh = () => setRefreshKey((prev) => prev + 1);

  useEffect(() => {
    fetch("http://localhost:5000/api/prices/live")
      .then((res) => res.json())
      .then((data) => setSymbols(Object.keys(data)))
      .catch((err) => console.error("Failed to load symbols", err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Crypto Trading Simulator</h1>

      {symbols.length > 0 ? (
        <BuyForm symbols={symbols} onBuySuccess={triggerRefresh} />
      ) : (
        <p>Loading Buy Form...</p>
      )}

      <SellForm onSellSuccess={triggerRefresh} />
      <AccountInfo key={refreshKey} />
      <Prices />
      <TransactionHistory refreshTrigger={refreshKey} />
      <ResetButton onReset={triggerRefresh} />
    </div>
  );
}

export default App;
