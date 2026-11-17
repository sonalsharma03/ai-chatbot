package com.example.chatbot;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
@Service
public class GeminiService {

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey =
            System.getenv("ChatBot_GEMINI_API_KEY");
    public String generateResponse(String message) {

        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(
                    Map.of(
                            "contents", List.of(
                                    Map.of(
                                            "parts", List.of(
                                                    Map.of("text", message)
                                            )
                                    )
                            )
                    )
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create Gemini request", e);
        }

        String responseBody = restClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent")
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        try {
            JsonNode root = objectMapper.readTree(responseBody);

            return root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .stringValue();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to read Gemini response", e);
        }
    }
}