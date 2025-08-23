package com.example.IncidentSubmissionService.llmService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@Slf4j
public class GeminiLlmService {

    @Value("${gemini.apiKey}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/";

    final static String CATEGORIES = "Network, Access, Software, Hardware, Email, Database, Security, Performance, Cloud, Configuration, User Request, General";

    public Map<String, String> getIncidentInsights(String description) {

        RestTemplate restTemplate = new RestTemplate();

        // Build request body
        Map<String, Object> request = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(Map.of("parts", List.of(Map.of("text",
                "You are an assistant that classifies IT support incidents.\n\n" +
                        "Task:\n" +
                        "1. Determine if the given text is a valid IT incident (login errors, server down, network outage, software crash, etc.).\n" +
                        "2. If valid: assign severity (Critical, High, Medium, Low) and category ("+CATEGORIES+").\n" +
                        "3. If NOT valid (greetings, random text, jokes, unrelated, empty): severity = \"N/A\" and category = \"N/A\".\n\n" +
                        "Return ONLY valid JSON in this format:\n" +
                        "{\n  \"severity\": \"...\",\n  \"category\": \"...\"\n}\n\n" +
                        "Incident: " + description
        ))));
        request.put("contents", contents);

        System.out.println("REQ");
        System.out.println(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                GEMINI_URL + model + ":generateContent?key="+ apiKey,
                entity,
                Map.class
        );

        // Extract text response
        Map<String, Object> resp = response.getBody();
        System.out.println("response from gemini");
        System.out.println(resp);

        // TODO: parse JSON properly (use Jackson)
        Map<String, String> result = getResponseInMap(resp);
        return result;
    }

    Map<String, String> getResponseInMap(Map<String,Object> response){
        // ✅ Extract the text
        Map<String, Object> candidate = (Map<String, Object>) ((List<?>) response.get("candidates")).get(0);
        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
        Map<String, Object> part = (Map<String, Object>) ((List<?>) content.get("parts")).get(0);

        String rawText = (String) part.get("text");
        System.out.println("Raw Gemini text:\n" + rawText);

        // ✅ Clean the string (remove ```json and ```)
        String cleanJson = rawText.replaceAll("```json", "").replaceAll("```", "").trim();
        System.out.println("Cleaned JSON:\n" + cleanJson);

        // ✅ Parse into Map<String, String>
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = null;
        try {
            result = mapper.readValue(cleanJson, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Error objectMapper {}",e.getMessage());
        }

        // ✅ Print result
        System.out.println("Parsed Map: " + result);
        System.out.println("Severity: " + result.get("severity"));
        System.out.println("Category: " + result.get("category"));
        return result;
    }
}
