
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function InternshipDetail() {
  const { id } = useParams();
  const [internship, setInternship] = useState(null);

  useEffect(()=>{
    fetch('/api/internships/' + id).then(r=>r.json()).then(setInternship).catch(()=>{});
  }, [id]);

  if (!internship) return <main className="container py-4">Loading...</main>;

  return (
    <main className="container py-4" style={{maxWidth:800}}>
      <h3>{internship.title}</h3>
      <div className="text-muted">{internship.company} • {internship.location}</div>
      <p className="mt-3">{internship.description}</p>
      <p>Stipend: {internship.stipend} • Duration: {internship.duration}</p>
      <button className="btn btn-primary" onClick={() => {
        const token = localStorage.getItem('token');
        if (!token) { window.location.href = '/login'; return; }
        fetch('/api/internships/' + id + '/apply', { method: 'POST', headers: { 'Authorization': 'Bearer ' + token } })
          .then(r => r.json()).then(j => alert(j.message || j.error || 'Response')).catch(()=>alert('Failed'));
      }}>Apply Now</button>
    </main>
  );
}
