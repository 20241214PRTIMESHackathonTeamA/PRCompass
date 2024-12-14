package com.example.prcompass.service;

import com.example.prcompass.controller.response.JudgeResult;
import org.springframework.stereotype.Service;

@Service
public class JudgeService {
    private static final String JUDGE_URL = "https://www.notion.so/";

    public JudgeResult getJudgeResult(String title) {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<SimilarResponse>> response = restTemplate.exchange(
//                JUDGE_URL,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//        List<SimilarResponse> res = response.getBody();


        var result = new JudgeResult(true, true, true);
        return result;
    }

}
