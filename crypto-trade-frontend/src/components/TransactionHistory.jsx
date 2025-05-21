import React, { useEffect, useState } from "react";

function TransactionHistory({refreshTrigger}) {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    fetchTransactions();
  }, [refreshTrigger]);

  const fetchTransactions = async () => {
    try {
      const res = await fetch("http://localhost:5000/api/account/transactions");
      if (!res.ok) throw new Error("Failed to fetch transactions");

      const data = await res.json();
      setTransactions(data);
    } catch (err) {
      console.error("Error fetching transactions:", err);
      alert("Failed to load transaction history.");
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "900px", margin: "0 auto" }}>
      <h2>Transaction History</h2>
      {transactions.length === 0 ? (
        <p>No transactions yet.</p>
      ) : (
        <table
          border="1"
          cellPadding="8"
          style={{ width: "100%", borderCollapse: "collapse" }}
        >
          <thead>
            <tr>
              <th>#</th>
              <th>Symbol</th>
              <th>Type</th>
              <th>Amount</th>
              <th>Price</th>
              <th>Total</th>
              <th>Timestamp</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((tx, index) => (
              <tr key={tx.id}>
                <td>{index + 1}</td>
                <td>{tx.symbol}</td>
                <td>{tx.action}</td>
                <td>{tx.amountCrypto}</td>
                <td>
                  {typeof tx.pricePerUnit === "number"
                    ? `$${tx.pricePerUnit.toFixed(2)}`
                    : "N/A"}
                </td>
                <td>
                  {typeof tx.amountCrypto === "number" && typeof tx.pricePerUnit === "number"
                    ? `$${(tx.amountCrypto * tx.pricePerUnit).toFixed(2)}`
                    : "N/A"}
                </td>
                <td>{new Date(tx.timestamp).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default TransactionHistory;
