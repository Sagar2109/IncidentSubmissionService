package com.example.IncidentSubmissionService.dao;

import java.util.List;

public interface IncidentSubmissionDao {
    List<String> getAllUniqueValuesByField(String fieldName);
}
