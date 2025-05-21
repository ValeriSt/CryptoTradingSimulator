import "./index.css";
import AccountInfo from "./components/AccountInfo";
import Prices from "./components/Prices";
import BuyForm from "./components/BuyForm";
import SellForm from "./components/SellForm";
import TransactionHistory from "./components/TransactionHistory";
import ResetButton from "./components/ResetButton";
import { useState } from "react";

function App() {
  const [refreshKey, setRefreshKey] = useState(0);

  const triggerRefresh = () => setRefreshKey((prev) => prev + 1);

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Crypto Trading Simulator</h1>
      <BuyForm onBuySuccess={triggerRefresh} />
      <SellForm onSellSuccess={triggerRefresh} />
      <AccountInfo key={refreshKey} />
      <Prices />

      <TransactionHistory refreshTrigger={refreshKey}/>
      <ResetButton onReset={triggerRefresh} />
    </div>
  );
}

export default App;
