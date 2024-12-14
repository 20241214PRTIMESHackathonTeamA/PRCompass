package com.example.prcompass.controller;

import com.example.prcompass.controller.response.RecommendResponse;
import com.example.prcompass.controller.response.SimilarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class PrCompassController {
    @GetMapping("/title-recommend")
    public ResponseEntity<List<RecommendResponse>> getTitleRecommendations() {
        var res1 = new RecommendResponse("1", "株式会社ほげ", "2024年12月14日", "Title 1", 10);
        var res2 = new RecommendResponse("2", "株式会社ふが", "2024年12月14日", "Title 2", 20);

        return ResponseEntity.ok(List.of(res1, res2));
    }

    @PostMapping("/judge")
    public ResponseEntity<Map<String, String>> judgeTitle(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String result = (title != null && !title.isEmpty()) ? "Accepted" : "Rejected";

        return ResponseEntity.ok(Map.of("result", result));
    }

    @PostMapping("/similar")
    public ResponseEntity<List<SimilarResponse>> findSimilarTitles(@RequestBody Map<String, String> request) {
        var res1 = new SimilarResponse("1", "株式会社ほげ", "2024年12月14日", "Title 1", 10);
        var res2 = new SimilarResponse("2", "株式会社ふが", "2024年12月14日", "Title 2", 20);

        return ResponseEntity.ok(List.of(res1, res2));
    }
}
