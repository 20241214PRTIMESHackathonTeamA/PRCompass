package com.example.prcompass.service;

import com.example.prcompass.controller.response.RecommendResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotionService {
    private static final String NOTION_URL = "https://script.googleusercontent.com/macros/echo?user_content_key=5Tpt1O8NX8ppm6djWu3ZwlpVAOVrYA1v2axR01CGFSaCTn2ohD0tw8jt7E6UQ1S6u0CdhJZbdjXXyIx3ct9iMVZP4dB2ZT23m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnDwqcXZ0NqGxxgK4PTHqIy1_9K9ryjzYthiabeJtbY5-wmatAxFVTJf3UY4ERL4cgnoowShy1nYgJPk1HCswUcSM8cXT7sK5dtz9Jw9Md8uu&lib=MdJdi8-sFOQFJRM5UGI_Wm6Df4atSk3bP";

    public RecommendResponse getRecommendations() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(NOTION_URL, String.class);

        List<String> newsTopics = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode newsTopicsNode = root.path("newsTopics");

            for (JsonNode topic : newsTopicsNode) {
                newsTopics.add(cleanString(topic.asText()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecommendResponse(newsTopics);
    }

    public String cleanString(String input) {
        // Remove the prefix and quotes
        return input.replaceAll("^\\d+\\.\\s*\"|\"$", "");
    }
}