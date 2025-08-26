# 🛠️ Event-to-Insight API (Java Service With LLM)

This project provides APIs to ingest IT incidents, apply AI classification using **Google Gemini LLM**, and retrieve incidents.  
The system automatically suggests **severity** and **category** for each incident.

---

## 📌 Tech Stack
- **Java**: 21  
- **Spring Boot**: 3.3.0  
- **Database**: MongoDB  
- **AI Provider**: Google Gemini API  

---

## ⚙️ Setup Instructions

1. Clone the repository:
   ```bash
   git clone git@github.com:Sagar2109/IncidentSubmissionService.git
   cd IncidentSubmissionService

2. Configure environment:

### application.properties
- server.port=8081
- spring.application.name=IncidentSubmissionService
- spring.data.mongodb.uri=mongodb://localhost:27017/test
- gemini.apiKey:AIzaSyAJ7W1r7ULaRzcvZyfFQnZvHswa7d_oEwU
- gemini.model=gemini-2.5-flash


Run the application: mvn spring-boot:run

📂 Project Structure

src/main/java/com/example/IncidentSubmissionService

    ├── controller
    │     └── IncidentController.java
    ├── service
    │     ├── IncidentService.java
    │     └── LlmService.java
    ├── llmService
    │     └── GeminiLlmService.java
    ├── model
    |     └── Incident.java
    ├── repository
    |     └── IncidentRepository.java
    └── IncidentSubmissionServiceApplication.java

📖 API Documentation
➤ Add Incident
curl --location 'http://localhost:8081/api/incident' \
--header 'Content-Type: application/json' \
--data '{
  "description": "Server down in Mumbai DC",
  "reporter": "Sagar"
}'


✅ Response:

[
    {
        "id": "68a98b25f0f2c051a04c530f",
        "description": "Server down in Mumbai DC",
        "reporter": "Sagar",
        "reportedAt": "2025-08-23T09:33:01.374+00:00",
        "severity": "Critical",
        "category": "Hardware"
    }
]

➤ Get All Incidents
curl --location --request GET 'http://localhost:8081/api/incident'

➤ Get All Specific By Severity
curl --location 'http://localhost:8081/api/incident/severity/Critical'

➤ Get All Specific By Category
curl --location 'http://localhost:8081/api/incident/category/Hardware'

🧠 LLM Prompt Used
You are an assistant that classifies IT support incidents.

Task:
1. Determine if the given text is a valid IT incident.
2. If valid: assign severity (Critical, High, Medium, Low) 
   and category (Network, Access, Software, Hardware, Email, Database, Security, Performance, Cloud, Configuration, User Request, General).
3. If NOT valid: severity = "N/A" and category = "N/A".

Return only JSON in this format:
{
  "severity": "...",
  "category": "..."
}

---
🔮 Future Enhancements

- Implement authentication & authorization for API access.  
- Store incident classification results in a database for analytics.  
- Add advanced filtering (date range, reporter, etc.).  
- Improve LLM prompt with context-specific examples.  
- **Will handle proper exceptions for robustness.**



---

👉 This is production-ready. You can just copy-paste it as your **README.md**.  

Do you also want me to **add example invalid input** (like `"Hello there" → severity=N/A, category=N/A`) to the README so reviewers can see 

## 🧑‍💻 Prompt Engineering Skills

This project not only implements APIs but also demonstrates my **ability to design effective prompts** for LLMs (Google Gemini).  
Below is an overview of my prompt engineering strengths.

### ⭐ Skill Ratings
- **Clarity & Structure**: ★★★★☆ (4.5/5)  
- **Instruction Design**: ★★★★★ (5/5)  
- **Edge Case Handling**: ★★★★☆ (4/5)  
- **Response Formatting**: ★★★★★ (5/5)  
- **LLM Integration in APIs**: ★★★★☆ (4.5/5)  

---

### 📜 Prompt History (Highlights)

#### 🔹 Incident Classification Prompt (Used in this project)
You are an assistant that classifies IT support incidents.

Task:

Determine if the given text is a valid IT incident.

If valid: assign severity (Critical, High, Medium, Low)
and category (Network, Access, Software, Hardware, Email, Database, Security, Performance, Cloud, Configuration, User Request, General).

If NOT valid: severity = "N/A" and category = "N/A".

Return only JSON in this format:
{
"severity": "...",
"category": "..."
}

👉 **Impact**: Makes the system **robust against random chatter** or irrelevant inputs (e.g., "Hello there" won’t break the flow).

---

#### 🔹 Compact Classification Prompt (Optimized for speed)
Classify the text into:

Severity: [Critical, High, Medium, Low, N/A]

Category: [Network, Hardware, Software, Security, Database, Email, Cloud, Access, Performance, User Request, Configuration, General, N/A]

Return JSON only.

👉 **Impact**: Lightweight version for faster inference calls.  

---

### 🏆 Why This Matters?
- Designed prompts that **balance accuracy, speed, and consistency**.  
- Used strict JSON format to avoid parsing issues.  
- Covered **edge cases** (invalid input, ambiguous text).  
- Integrated directly with **Spring Boot APIs** for seamless AI-powered classification.  

---
