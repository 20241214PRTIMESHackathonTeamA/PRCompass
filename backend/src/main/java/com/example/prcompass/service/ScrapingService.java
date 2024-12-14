package com.example.prcompass.service;

import com.example.prcompass.controller.response.SimilarResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {
    private static final String SCRAPING_URL = "https://www.notion.so/";

    public List<SimilarResponse> getSimilar(String title) {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<SimilarResponse>> response = restTemplate.exchange(
//                SCRAPING_URL,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//        List<SimilarResponse> res = response.getBody();

        var res1 = new SimilarResponse("1", "株式会社ほげ", "2024年12月14日", "Title 1", 10);
        var res2 = new SimilarResponse("2", "株式会社ふが", "2024年12月14日", "Title 2", 20);
        List<SimilarResponse> res = List.of(res1, res2);

        return res;
    }
}