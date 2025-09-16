
package com.example.portal.controller;

import com.example.portal.model.User;
import com.example.portal.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String fullName = body.get("fullName");
        String password = body.get("password");
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password required"));
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }
        User u = new User();
        u.setEmail(email);
        u.setFullName(fullName == null ? "" : fullName);
        u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("message", "User created"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Optional<User> ou = userRepository.findByEmail(email);
        if (ou.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "Invalid creds"));
        User u = ou.get();
        if (!BCrypt.checkpw(password, u.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid creds"));
        }
        String token = UUID.randomUUID().toString();
        u.setToken(token);
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("token", token, "email", u.getEmail(), "fullName", u.getFullName()));
    }
}
