package com.example.IncidentSubmissionService.service;

import com.example.IncidentSubmissionService.llmService.GeminiLlmService;
import com.example.IncidentSubmissionService.repository.IncidentRepository;
import com.example.IncidentSubmissionService.model.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private GeminiLlmService geminiLlmService;

    public Incident createIncident(String description, String reporter) {
        Incident incident = new Incident();
        incident.setDescription(description);
        incident.setReporter(reporter);
        incident.setReportedAt(new Date());


        Map<String, String> insights = geminiLlmService.getIncidentInsights(description);
        incident.setSeverity(insights.get("severity"));
        incident.setCategory(insights.get("category"));
        //TO-DO pending Check the Expection handelling
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public List<Incident> getBySeverity(String severity) {
        return incidentRepository.findBySeverity(severity);
    }

    public List<Incident> getByCategory(String category) {
        return incidentRepository.findByCategory(category);
    }
}
