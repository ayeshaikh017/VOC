package com.example.portal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Value("${portal.upload-dir}")
    private String uploadDir;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> serve(@PathVariable String filename) {
        File f = new File(uploadDir, filename);
        if (!f.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource res = new FileSystemResource(f);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + f.getName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(res);
    }
}
