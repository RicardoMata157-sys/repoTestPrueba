import { useEffect, useState } from 'react'
import api from '../services/api'

const emptyForm = { fullName: '', email: '', password: '' }

export default function UsersPage() {
  const [users, setUsers] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [editingId, setEditingId] = useState(null)
  const [error, setError] = useState('')

  const loadUsers = async () => {
    const { data } = await api.get('/users')
    setUsers(data)
  }

  useEffect(() => { loadUsers() }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      if (editingId) {
        await api.put(`/users/${editingId}`, form)
      } else {
        await api.post('/users', form)
      }
      setForm(emptyForm)
      setEditingId(null)
      loadUsers()
    } catch (err) {
      setError(err.response?.data?.message || 'Unable to save user')
    }
  }

  const handleEdit = (user) => {
    setEditingId(user.id)
    setForm({ fullName: user.fullName, email: user.email, password: '' })
  }

  const handleDelete = async (id) => {
    await api.delete(`/users/${id}`)
    loadUsers()
  }

  return (
    <div className="grid twoCols">
      <div className="card">
        <h2>{editingId ? 'Edit user' : 'Create user'}</h2>
        <form onSubmit={handleSubmit} className="stack">
          <input placeholder="Full name" value={form.fullName} onChange={(e) => setForm({ ...form, fullName: e.target.value })} />
          <input placeholder="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          <input placeholder="Password" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
          {error && <p className="error">{error}</p>}
          <button type="submit">{editingId ? 'Update' : 'Save'}</button>
        </form>
      </div>
      <div className="card">
        <h2>Users</h2>
        <div className="list">
          {users.map(user => (
            <div key={user.id} className="rowItem">
              <div>
                <strong>{user.fullName}</strong>
                <p>{user.email}</p>
              </div>
              <div className="actions">
                <button onClick={() => handleEdit(user)}>Edit</button>
                <button className="danger" onClick={() => handleDelete(user.id)}>Delete</button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
