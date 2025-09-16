
import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Profile from './pages/Profile';
import InternshipDetail from './pages/InternshipDetail';

function App() {
  return (
    <BrowserRouter>
      <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
        <div className="container">
          <Link className="navbar-brand fw-bold" to="/">Internship Portal</Link>
          <div className="d-flex">
            <Link className="btn btn-outline-primary me-2" to="/profile">Profile</Link>
            <button className="btn btn-outline-danger" onClick={() => { localStorage.removeItem('token'); window.location.href = '/'; }}>Logout</button>
          </div>
        </div>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/internships/:id" element={<InternshipDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
