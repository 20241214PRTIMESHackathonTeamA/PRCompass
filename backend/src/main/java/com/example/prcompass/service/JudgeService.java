package com.example.prcompass.service;

import com.example.prcompass.controller.response.JudgeResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JudgeService {
    private static final String JUDGE_URL = "https://script.google.com/macros/s/AKfycbzGCE2xEUQmwZFSnQi0PZGcRE67NK2fput2ZFsw7W1bdlD0n3x-eeJ7Ek3oqy4NoSLI/exec";

    public JudgeResult getJudgeResult(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(JUDGE_URL)
                .queryParam("title", title)
                .toUriString();
        ResponseEntity<JudgeResult> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        JudgeResult result = response.getBody();

//        var result = new JudgeResult(
//                new JudgeResult.ValidationResult(true, "Valid news"),
//                new JudgeResult.ValidationResult(true, "Public decency is maintained"),
//                new JudgeResult.ValidationResult(true, "Legal compliance is maintained")
//        );
        return result;
    }

}
