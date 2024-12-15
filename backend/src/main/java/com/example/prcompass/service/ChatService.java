package com.example.prcompass.service;

import com.example.prcompass.controller.request.ChatRequestDTO;
import com.example.prcompass.controller.response.ChatResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {
    private static final String URL = "https://judgeapi.fly.dev/chat";

    public ChatResponseDTO getResponse(ChatRequestDTO chatRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // リクエストの設定
        HttpEntity<ChatRequestDTO> request = new HttpEntity<>(chatRequest, headers);

        // 外部APIにPOSTリクエストを送信
        ResponseEntity<ChatResponseDTO> response = restTemplate.exchange(
                URL,  // 外部APIのURL
                HttpMethod.POST,
                request,
                ChatResponseDTO.class
        );


        return response.getBody();
    }
}
