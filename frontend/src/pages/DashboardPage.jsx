import { useAuth } from '../context/AuthContext'

export default function DashboardPage() {
  const { user } = useAuth()

  return (
    <div>
      <h2>Dashboard</h2>
      <div className="grid twoCols">
        <div className="card">
          <h3>Welcome</h3>
          <p>Logged in as <strong>{user?.fullName}</strong></p>
          <p>This project simulates card payments with JWT auth, encrypted sensitive fields and CRUD operations.</p>
        </div>
        <div className="card">
          <h3>Security notes</h3>
          <ul>
            <li>Passwords hashed with BCrypt.</li>
            <li>JWT used for stateless auth.</li>
            <li>CVV is validated but never stored.</li>
            <li>Cardholder name is encrypted with AES.</li>
          </ul>
        </div>
      </div>
    </div>
  )
}
