
package com.example.portal.controller;

import com.example.portal.model.User;
import com.example.portal.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    private final UserRepository userRepository;

    @Value("${portal.upload-dir}")
    private String uploadDir;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        String token = auth.substring(7);
        Optional<User> ou = userRepository.findByToken(token);
        if (ou.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        User u = ou.get();
        u.setPassword(null);
        return ResponseEntity.ok(u);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization", required = false) String auth, @RequestBody Map<String,String> body) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        String token = auth.substring(7);
        Optional<User> ou = userRepository.findByToken(token);
        if (ou.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        User u = ou.get();
        if (body.containsKey("fullName")) u.setFullName(body.get("fullName"));
        if (body.containsKey("headline")) u.setHeadline(body.get("headline"));
        if (body.containsKey("bio")) u.setBio(body.get("bio"));
        if (body.containsKey("skills")) u.setSkills(body.get("skills"));
        userRepository.save(u);
        u.setPassword(null);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestHeader(value = "Authorization", required = false) String auth, @RequestParam("file") MultipartFile file) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        String token = auth.substring(7);
        Optional<User> ou = userRepository.findByToken(token);
        if (ou.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","Unauthorized"));
        User u = ou.get();

        if (file == null || file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","File required"));
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String name = UUID.randomUUID().toString() + (ext != null ? "." + ext : "");
        try {
            File dest = new File(uploadDir, name);
            file.transferTo(dest);
            u.setCvFileName(name);
            userRepository.save(u);
            return ResponseEntity.ok(Map.of("message","Uploaded","file",name));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error","Upload failed"));
        }
    }
}
