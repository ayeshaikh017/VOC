
package com.example.portal;

import com.example.portal.model.Internship;
import com.example.portal.model.User;
import com.example.portal.repo.InternshipRepository;
import com.example.portal.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PortalApplication {

    @Value("${portal.upload-dir}")
    private String uploadDir;

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

    @Bean
    CommandLineRunner init(InternshipRepository internships, UserRepository users) {
        return args -> {
            try {
                Files.createDirectories(Path.of(uploadDir));
            } catch (Exception ignored) {}

            if (internships.count() == 0) {
                internships.saveAll(List.of(
                    new Internship(null, "Java Backend Intern", "TechNova", "Mumbai", "10000", "3 months", "Work with Spring Boot, REST APIs, and databases.", LocalDateTime.now()),
                    new Internship(null, "Frontend React Intern", "PixelWorks", "Pune", "8000", "3 months", "Build UI components with React, TypeScript, and Tailwind.", LocalDateTime.now().minusDays(1)),
                    new Internship(null, "Data Analyst Intern", "InsightAI", "Remote", "7000", "2 months", "Assist in data cleaning, EDA, and dashboarding.", LocalDateTime.now().minusDays(2)),
                    new Internship(null, "Android Developer Intern", "Mobify", "Bengaluru", "9000", "3 months", "Jetpack Compose, Kotlin coroutines, REST APIs.", LocalDateTime.now().minusDays(3)),
                    new Internship(null, "ML Intern", "VisionLabs", "Hyderabad", "12000", "4 months", "Model training, MLOps basics, and experimentation.", LocalDateTime.now().minusDays(4))
                ));
            }

            if (users.count() == 0) {
                User u = new User();
                u.setEmail("student@example.com");
                u.setFullName("Demo Student");
                u.setPassword("$2a$10$7q2M1n2A8qYFZ8a1D1NwOOVqz0i3eYb7fUQe0qj3v9y1k0Zf8p8Sa"); // bcrypt 'password'
                users.save(u);
            }
        };
    }
}
