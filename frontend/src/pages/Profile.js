
import React, { useEffect, useState } from 'react';

export default function Profile() {
  const [user, setUser] = useState(null);
  const [file, setFile] = useState(null);

  useEffect(()=> {
    const token = localStorage.getItem('token');
    if (!token) { window.location.href = '/login'; return; }
    fetch('/api/profile', { headers: { 'Authorization': 'Bearer ' + token }})
      .then(r=>r.json()).then(setUser).catch(()=>{});
  }, []);

  function save(e) {
    e.preventDefault();
    const token = localStorage.getItem('token');
    const body = { fullName: user.fullName, headline: user.headline || '', bio: user.bio || '', skills: user.skills || '' };
    fetch('/api/profile', { method: 'PUT', headers: {'Content-Type':'application/json', 'Authorization': 'Bearer ' + token}, body: JSON.stringify(body) })
      .then(r=>r.json()).then(j=>{ alert('Saved'); setUser(j); }).catch(()=>alert('Failed'));
  }

  function upload(e) {
    e.preventDefault();
    const token = localStorage.getItem('token');
    if (!file) return alert('Choose file');
    const fd = new FormData();
    fd.append('file', file);
    fetch('/api/profile/upload', { method: 'POST', headers: { 'Authorization': 'Bearer ' + token }, body: fd })
      .then(r=>r.json()).then(j=>{ alert('Uploaded'); window.location.reload(); }).catch(()=>alert('Upload failed'));
  }

  if (!user) return <main className="container py-4">Loading...</main>;

  return (
    <main className="container py-4" style={{maxWidth:800}}>
      <h3>Profile</h3>
      <form onSubmit={save}>
        <div className="mb-3">
          <label className="form-label">Full Name</label>
          <input className="form-control" value={user.fullName || ''} onChange={e=>setUser({...user, fullName: e.target.value})} />
        </div>
        <div className="mb-3">
          <label className="form-label">Headline</label>
          <input className="form-control" value={user.headline || ''} onChange={e=>setUser({...user, headline: e.target.value})} />
        </div>
        <div className="mb-3">
          <label className="form-label">Skills</label>
          <input className="form-control" value={user.skills || ''} onChange={e=>setUser({...user, skills: e.target.value})} />
        </div>
        <div className="mb-3">
          <label className="form-label">Bio</label>
          <textarea className="form-control" value={user.bio || ''} onChange={e=>setUser({...user, bio: e.target.value})} />
        </div>
        <button className="btn btn-primary me-2">Save</button>
      </form>

      <hr />
      <h5>Resume</h5>
      {user.cvFileName ? <p>Current resume: <a href={'/api/files/' + user.cvFileName} target="_blank" rel="noreferrer">View / Download</a></p> : <p>No resume uploaded</p>}
      <form onSubmit={upload}>
        <div className="mb-3">
          <input type="file" accept="application/pdf" className="form-control" onChange={e=>setFile(e.target.files[0])} />
        </div>
        <button className="btn btn-outline-primary">Upload Resume</button>
      </form>
    </main>
  );
}
