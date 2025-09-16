
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Signup() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [fullName, setFullName] = useState('');
  const navigate = useNavigate();

  const submit = (e) => {
    e.preventDefault();
    fetch('/api/auth/signup', { method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify({email, password, fullName}) })
      .then(async res => {
        const j = await res.json();
        if (!res.ok) { alert(j.error || 'Signup failed'); return; }
        alert('Signup success. Login now.');
        navigate('/login');
      }).catch(err => alert('Error'));
  };

  return (
    <main className="container py-4" style={{maxWidth:600}}>
      <h3>Signup</h3>
      <form onSubmit={submit}>
        <div className="mb-3">
          <label className="form-label">Full Name</label>
          <input className="form-control" value={fullName} onChange={e=>setFullName(e.target.value)} />
        </div>
        <div className="mb-3">
          <label className="form-label">Email</label>
          <input className="form-control" value={email} onChange={e=>setEmail(e.target.value)} />
        </div>
        <div className="mb-3">
          <label className="form-label">Password</label>
          <input type="password" className="form-control" value={password} onChange={e=>setPassword(e.target.value)} />
        </div>
        <button className="btn btn-primary">Signup</button>
      </form>
    </main>
  );
}
