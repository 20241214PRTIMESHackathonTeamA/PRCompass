package com.example.prcompass.service;

import com.example.prcompass.controller.response.JudgeResult;
import com.example.prcompass.service.dto.JudgeResultDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class JudgeService {
    private static final String JUDGE_URL = "https://judgeapi.fly.dev/evaluate-title";

    public JudgeResult getJudgeResult(String title) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(JUDGE_URL).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", title);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // API呼び出し
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        // レスポンスから "evaluation" フィールドを取り出す
        String evaluationJson = response.getBody().get("evaluation");

        // 文字列として取得した JSON を ResponseDTO.Evaluation オブジェクトに変換
        ObjectMapper objectMapper = new ObjectMapper();
        JudgeResultDto.Evaluation evaluation = objectMapper.readValue(evaluationJson, JudgeResultDto.Evaluation.class);


        return new JudgeResult(
                new JudgeResult.ValidationResult(evaluation.getNewsValue().isValid(), evaluation.getNewsValue().getReason()),
                new JudgeResult.ValidationResult(evaluation.getPublicDecency().isValid(), evaluation.getPublicDecency().getReason()),
                new JudgeResult.ValidationResult(evaluation.getLegalCompliance().isValid(), evaluation.getLegalCompliance().getReason())
        );
    }

}
