import React, { useEffect, useState } from "react";
import './AccountInfo.css'

function AccountInfo() {
  const [balance, setBalance] = useState(null);
  const [holdings, setHoldings] = useState([]);

  useEffect(() => {
    fetch("http://localhost:5000/api/account/balance")
      .then((res) => res.json())
      .then(setBalance)
      .catch((err) => console.error("Error fetching balance:", err));

    fetch("http://localhost:5000/api/account/holdings")
      .then((res) => res.json())
      .then(setHoldings)
      .catch((err) => console.error("Error fetching holdings:", err));
  }, []);

  return (
    <div className="account-info" style={{ marginBottom: "2rem" }}>
      <h2>Account Info</h2>
      <p><strong>Balance:</strong> ${balance?.toFixed(2)}</p>

      {holdings.length === 0 ? (
        <p>No holdings yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Symbol</th>
              <th>Amount</th>
            </tr>
          </thead>
          <tbody>
            {holdings.map((h) => (
              <tr key={h.symbol}>
                <td>{h.symbol}</td>
                <td>{h.amount.toFixed(6)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default AccountInfo;
