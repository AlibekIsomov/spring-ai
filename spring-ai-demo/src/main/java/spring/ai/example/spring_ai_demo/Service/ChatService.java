package spring.ai.example.spring_ai_demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String callChatGPT(String message) {
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create request payload for chat-based model
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");  // Use supported model
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", message)
        ));
        requestBody.put("max_tokens", 100);

        // Convert to JSON
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.valueToTree(requestBody).toString(), headers);

        // Send POST request
        ResponseEntity<JsonNode> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, JsonNode.class);

        // Extract and return the response
        JsonNode responseBody = response.getBody();
        if (responseBody != null && responseBody.has("choices")) {
            return responseBody.get("choices").get(0).get("message").get("content").asText();
        }
        return "Error: Could not retrieve response from ChatGPT";
    }
}
