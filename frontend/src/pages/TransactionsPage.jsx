import { useEffect, useState } from 'react'
import api from '../services/api'

export default function TransactionsPage() {
  const [transactions, setTransactions] = useState([])

  useEffect(() => {
    api.get('/transactions').then(({ data }) => setTransactions(data))
  }, [])

  return (
    <div className="card">
      <h2>Transactions</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>User</th>
            <th>Holder</th>
            <th>Last 4</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map(tx => (
            <tr key={tx.id}>
              <td>{tx.id}</td>
              <td>{tx.userId}</td>
              <td>{tx.cardHolderName}</td>
              <td>{tx.cardLast4}</td>
              <td>{tx.amount} {tx.currency}</td>
              <td>{tx.status}</td>
              <td>{new Date(tx.createdAt).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
