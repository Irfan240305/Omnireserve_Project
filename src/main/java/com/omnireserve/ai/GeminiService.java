package com.omnireserve.ai;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;



@Service
public class GeminiService {

  @Value("${gemini.api.key}")
  private String apiKey;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public BookingExtraction extractBookingInfo(String userMessage) {

    // 1. Build the prompt
    String prompt = """
        Extract the seat number and user ID from this booking request.
        Return ONLY a JSON object in exactly this format, with no other text:
        {
          "seatNumber": "<seat>",
          "userId": <user_id>
        }
        Booking request: "%s"
        """.formatted(userMessage);

    // 2. Build the request body gemini expects
    Map<String, Object> requestBody = Map.of(
      "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
    );

    // 3. Build the endpoint url with api key
    String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + apiKey;

    // 4. Make the HTTP POST call using RestClient
    RestClient restClient = RestClient.create();
    String response = restClient.post()
      .uri(url)
      .body(requestBody)
      .retrieve()
      .body(String.class);

    // 5. Parse the response to extract the buried JSON
    try {
      JsonNode root = objectMapper.readTree(response);
      String innerJson = root
        .path("candidates").get(0)
        .path("content")
        .path("parts").get(0)
        .path("text")
        .asText();
      return objectMapper.readValue(innerJson, BookingExtraction.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse Gemini response", e);
    }
  }

}