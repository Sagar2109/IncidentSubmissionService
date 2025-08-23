package com.example.IncidentSubmissionService.controller;

import com.example.IncidentSubmissionService.model.Incident;
import com.example.IncidentSubmissionService.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incident")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    // Create incident
    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Map<String, String> req) {
        String description = req.get("description");
        String reporter = req.get("reporter");

        Incident incident = incidentService.createIncident(description, reporter);
        return ResponseEntity.ok(incident);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Incident>> getAll() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }

    // Get by severity
    @GetMapping("/severity/{level}")
    public ResponseEntity<List<Incident>> getBySeverity(@PathVariable String level) {
        return ResponseEntity.ok(incidentService.getBySeverity(level));
    }

    // Get by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Incident>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(incidentService.getByCategory(category));
    }
}
