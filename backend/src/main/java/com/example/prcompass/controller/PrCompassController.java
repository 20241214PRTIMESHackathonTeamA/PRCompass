package com.example.prcompass.controller;

import com.example.prcompass.controller.response.JudgeResult;
import com.example.prcompass.controller.response.RecommendResponse;
import com.example.prcompass.controller.response.SimilarResponse;
import com.example.prcompass.service.JudgeService;
import com.example.prcompass.service.NotionService;
import com.example.prcompass.service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrCompassController {
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private NotionService notionService;
    @Autowired
    private ScrapingService scrapingService;


    @GetMapping("/title-recommend")
    public ResponseEntity<RecommendResponse> getTitleRecommendations() {
        var result = notionService.getRecommendations();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/judge")
    public ResponseEntity<JudgeResult> judgeTitle(@RequestBody String title) {
        var result = judgeService.getJudgeResult(title);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/similar")
    public ResponseEntity<List<SimilarResponse>> findSimilarTitles(@RequestBody String title) {
        var result = scrapingService.getSimilar(title);
        if (result != null && result.size() > 5) {
            // Collections.shuffle(result);
            result = result.subList(0, 5);
        }
        return ResponseEntity.ok(result);
    }
}
