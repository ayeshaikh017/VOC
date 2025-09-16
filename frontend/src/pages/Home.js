// import React, { useEffect, useState } from 'react';
// import { Link } from 'react-router-dom';

// export default function Home() {
//   const [internships, setInternships] = useState([]);

//   useEffect(() => {
//     fetch('/api/internships')
//       .then(r => r.json())
//       .then(setInternships)
//       .catch(console.error);
//   }, []);

//   return (
//     <main className="container py-4">
//       {/* Header with Signup button */}
//       <div className="d-flex justify-content-between align-items-center mb-3">
//         <h3>Available Internships</h3>
//         <Link to="/signup" className="btn btn-success">Signup</Link>
//       </div>

//       <div className="row g-3 mt-2">
//         {internships.map(i => (
//           <div className="col-md-6" key={i.id}>
//             <div className="card p-3">
//               <h5>{i.title}</h5>
//               <div className="text-muted">{i.company} • {i.location}</div>
//               <p className="mb-1">Stipend: {i.stipend} • Duration: {i.duration}</p>
//               <div className="d-flex gap-2">
//                 <Link to={'/internships/' + i.id} className="btn btn-primary btn-sm">View</Link>
//                 <button className="btn btn-outline-primary btn-sm" onClick={() => {
//                   const token = localStorage.getItem('token');
//                   if (!token) { window.location.href = '/login'; return; }
//                   fetch('/api/internships/' + i.id + '/apply', { 
//                     method: 'POST', 
//                     headers: { 'Authorization': 'Bearer ' + token } 
//                   })
//                     .then(r => r.json())
//                     .then(j => {
//                       alert(j.message || j.error || 'Response');
//                     })
//                     .catch(() => alert('Failed'));
//                 }}>Apply</button>
//               </div>
//             </div>
//           </div>
//         ))}
//       </div>
//     </main>
//   );
// }






// import React, { useEffect, useState } from 'react';
// import { Link } from 'react-router-dom';

// export default function Home() {
//   const [internships, setInternships] = useState([]);

//   useEffect(() => {
//     fetch('/api/internships')
//       .then(r => r.json())
//       .then(setInternships)
//       .catch(console.error);
//   }, []);

//   return (
//     <main className="container py-4">
//       <h3 className="mb-4">Available Internships</h3>
//       <div className="row g-4">
//         {internships.map(i => (
//           <div className="col-md-6 col-lg-6" key={i.id}>
//             <div className="card p-3 h-100 shadow-sm">
//               <h5 className="text-light">{i.title}</h5>
//               <div className="text-muted">{i.company} • {i.location}</div>
//               <p className="mb-1">Stipend: {i.stipend} • Duration: {i.duration}</p>
//               <div className="d-flex gap-2 mt-2">
//                 <Link to={'/internships/' + i.id} className="btn btn-primary btn-sm">View</Link>
//                 <button className="btn btn-outline-primary btn-sm" onClick={() => {
//                   const token = localStorage.getItem('token');
//                   if (!token) { window.location.href = '/login'; return; }
//                   fetch('/api/internships/' + i.id + '/apply', {
//                     method: 'POST',
//                     headers: { 'Authorization': 'Bearer ' + token }
//                   })
//                     .then(r => r.json()).then(j => {
//                       alert(j.message || j.error || 'Response');
//                     }).catch(e => alert('Failed'));
//                 }}>Apply</button>
//               </div>
//             </div>
//           </div>
//         ))}
//       </div>
//     </main>
//   );
// }





import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
  const [internships, setInternships] = useState([]);

  useEffect(() => {
    fetch('/api/internships')
      .then(r => r.json())
      .then(setInternships)
      .catch(console.error);
  }, []);

  return (
    <main className="container py-4">
      {/* Header Row */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3 className="mb-0">Available Internships</h3>
        <Link to="/signup" className="btn btn-success">Signup</Link>
      </div>

      {/* Internship Cards */}
      <div className="row g-4">
        {internships.map(i => (
          <div className="col-md-6 col-lg-6" key={i.id}>
            <div className="card p-3 h-100 shadow-sm">
              <h5 className="text-light">{i.title}</h5>
              <div className="text-muted">{i.company} • {i.location}</div>
              <p className="mb-1">Stipend: {i.stipend} • Duration: {i.duration}</p>
              <div className="d-flex gap-2 mt-2">
                <Link to={'/internships/' + i.id} className="btn btn-primary btn-sm">View</Link>
                <button className="btn btn-outline-primary btn-sm" onClick={() => {
                  const token = localStorage.getItem('token');
                  if (!token) { window.location.href = '/login'; return; }
                  fetch('/api/internships/' + i.id + '/apply', {
                    method: 'POST',
                    headers: { 'Authorization': 'Bearer ' + token }
                  })
                    .then(r => r.json()).then(j => {
                      alert(j.message || j.error || 'Response');
                    }).catch(e => alert('Failed'));
                }}>Apply</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
