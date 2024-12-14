package com.example.prcompass.service;

import com.example.prcompass.controller.response.RecommendResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotionService {
    private static final String NOTION_URL = "https://www.notion.so/";

    public List<RecommendResponse> getRecommendations() {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(NOTION_URL, String.class);
//
//        List<RecommendResponse> recommendations = new ArrayList<>();
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode root = objectMapper.readTree(response.getBody());
//            JsonNode children = root.path("notionData").path("children");
//
//            for (JsonNode child : children) {
//                JsonNode page = child.path("page");
//                String id = page.path("id").asText();
//                String title = page.path("title").asText();
//                recommendations.add(new RecommendResponse(id, title));
//
//                if (recommendations.size() == 3) {
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        var res1 = new RecommendResponse("1", "Title 1");
        var res2 = new RecommendResponse("2", "Title 2");
        List<RecommendResponse> recommendations = List.of(res1, res2);
        return recommendations;
    }
}