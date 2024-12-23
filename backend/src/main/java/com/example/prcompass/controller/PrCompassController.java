package com.example.prcompass.controller;

import com.example.prcompass.controller.request.ChatRequestDTO;
import com.example.prcompass.controller.request.TitleRequest;
import com.example.prcompass.controller.response.ChatResponseDTO;
import com.example.prcompass.controller.response.JudgeResult;
import com.example.prcompass.controller.response.RecommendResponse;
import com.example.prcompass.controller.response.SimilarResponse;
import com.example.prcompass.service.ChatService;
import com.example.prcompass.service.JudgeService;
import com.example.prcompass.service.NotionService;
import com.example.prcompass.service.ScrapingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PrCompassController {
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private NotionService notionService;
    @Autowired
    private ScrapingService scrapingService;
    @Autowired
    private ChatService chatService;


    @GetMapping("/title-recommend")
    public ResponseEntity<RecommendResponse> getTitleRecommendations(@RequestParam(required = false) boolean refresh) {
        var result = notionService.getRecommendations(refresh);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/judge")
    public ResponseEntity<JudgeResult> judgeTitle(@RequestBody TitleRequest title) throws JsonProcessingException {
        var result = judgeService.getJudgeResult(title.getTitle());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/similar")
    public ResponseEntity<List<SimilarResponse>> findSimilarTitles(@RequestBody TitleRequest title) {
        var result = scrapingService.getSimilar(title.getTitle());
        if (result != null && result.size() > 5) {
            // Collections.shuffle(result);
            result = result.subList(0, 5);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO chatRequest) {
        // 外部APIにリクエストを送信するための準備
        var result = chatService.getResponse(chatRequest);


        // レスポンスをそのまま返す
        return ResponseEntity.ok(result);
    }
}
