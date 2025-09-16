
package com.example.portal.controller;

import com.example.portal.model.Application;
import com.example.portal.model.Internship;
import com.example.portal.model.User;
import com.example.portal.repo.ApplicationRepository;
import com.example.portal.repo.InternshipRepository;
import com.example.portal.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
@CrossOrigin(origins = "http://localhost:3000")
public class InternshipController {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public InternshipController(InternshipRepository internshipRepository, UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.internshipRepository = internshipRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public List<Internship> list(@RequestParam(value = "q", required = false) String q) {
        if (q != null && !q.isBlank()) {
            return internshipRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(q, q);
        }
        return internshipRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Internship> oi = internshipRepository.findById(id);
        return oi.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        String token = auth.substring(7);
        Optional<User> ou = userRepository.findByToken(token);
        if (ou.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        User u = ou.get();
        Optional<Internship> oi = internshipRepository.findById(id);
        if (oi.isEmpty()) return ResponseEntity.status(404).body(Map.of("error", "Internship not found"));
        Application app = new Application();
        app.setUser(u);
        app.setInternship(oi.get());
        app.setAppliedAt(LocalDateTime.now());
        applicationRepository.save(app);
        return ResponseEntity.ok(Map.of("message", "Applied successfully"));
    }
}
