import { useState } from 'react'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function PaymentsPage() {
  const { user } = useAuth()

  const [form, setForm] = useState({
    cardHolderName: user?.fullName || '',
    cardNumber: '4111111111111112',
    expiry: '12/30',
    cvv: '123',
    amount: '250.00',
    currency: 'MXN'
  })

  const [result, setResult] = useState(null)
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setMessage('')
    setResult(null)

    try {
      const payload = {
        ...form,
        userId: user.userId,
        amount: Number(form.amount)
      }

      const { data } = await api.post('/transactions/process', payload)

      setMessage(data.message)
      setResult(data.data)

      alert(data.message)
      console.log('Transacción guardada:', data.data)
    } catch (err) {
      setError(err.response?.data?.message || 'Payment failed')
    }
  }

  return (
    <div className="grid twoCols">
      <div className="card">
        <h2>Payment simulator</h2>

        <form className="stack" onSubmit={handleSubmit}>
          <input
            placeholder="Cardholder name"
            value={form.cardHolderName}
            onChange={(e) => setForm({ ...form, cardHolderName: e.target.value })}
          />

          <input
            placeholder="Card number"
            value={form.cardNumber}
            onChange={(e) =>
              setForm({ ...form, cardNumber: e.target.value.replace(/\D/g, '').slice(0, 16) })
            }
          />

          <div className="inlineFields">
            <input
              placeholder="MM/YY"
              value={form.expiry}
              onChange={(e) => setForm({ ...form, expiry: e.target.value })}
            />

            <input
              placeholder="CVV"
              value={form.cvv}
              onChange={(e) =>
                setForm({ ...form, cvv: e.target.value.replace(/\D/g, '').slice(0, 4) })
              }
            />
          </div>

          <div className="inlineFields">
            <input
              placeholder="Amount"
              type="number"
              value={form.amount}
              onChange={(e) => setForm({ ...form, amount: e.target.value })}
            />

            <input
              placeholder="Currency"
              value={form.currency}
              onChange={(e) =>
                setForm({ ...form, currency: e.target.value.toUpperCase().slice(0, 3) })
              }
            />
          </div>

          {message && <p className="success">{message}</p>}
          {error && <p className="error">{error}</p>}

          <button type="submit">Process payment</button>
        </form>
      </div>

      <div className="card">
        <h2>Last result</h2>
        {result ? (
          <div className="stack">
            <p><strong>Status:</strong> {result.status}</p>
            <p><strong>Amount:</strong> {result.amount} {result.currency}</p>
            <p><strong>Card:</strong> **** **** **** {result.cardLast4}</p>
            <p><strong>Token:</strong> {result.token}</p>
            <p><strong>Holder:</strong> {result.cardHolderName}</p>
          </div>
        ) : (
          <p>No payment processed yet.</p>
        )}
      </div>
    </div>
  )
}