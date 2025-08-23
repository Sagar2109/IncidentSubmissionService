package com.example.IncidentSubmissionService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "incidents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    @Id
    private String id;

    private String description;   // e.g., "Server is down"
    private String reporter;      // who reported
    private Date reportedAt;      // when reported

    // AI processed fields
    private String severity;      // e.g., Critical, High, Medium, Low
    private String category;      // e.g., Network, Software, Access, Hardware
}
