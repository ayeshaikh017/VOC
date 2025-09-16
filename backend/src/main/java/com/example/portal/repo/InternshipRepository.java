
package com.example.portal.repo;

import com.example.portal.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String title, String company);
}
