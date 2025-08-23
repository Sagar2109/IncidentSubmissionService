package com.example.IncidentSubmissionService.repository;

import com.example.IncidentSubmissionService.model.Incident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends MongoRepository<Incident, String> {
    List<Incident> findByCategory(String category);
    List<Incident> findBySeverity(String severity);
}
