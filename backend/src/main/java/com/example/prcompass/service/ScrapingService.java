package com.example.prcompass.service;

import com.example.prcompass.controller.response.SimilarResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ScrapingService {
    private static final String SCRAPING_URL = "https://prtimes-scraping-api.onrender.com/prtimes_posts";

    public List<SimilarResponse> getSimilar(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(SCRAPING_URL)
                .queryParam("keyword", title)
                .toUriString();
        ResponseEntity<List<SimilarResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<SimilarResponse> res = response.getBody();

//        var res1 = new SimilarResponse("株式会社ほげ", "2024年12月14日", "Title 1", "https://example.com/xxxx", "/main/html/rd/p/xxxxxxx.xxxxxxxxxx", 10);
//        var res2 = new SimilarResponse("株式会社ふが", "2024年12月14日", "Title 2", "https://example.com/xxxx", "/main/html/rd/p/xxxxxxx.xxxxxxxxxx", 20);
//        List<SimilarResponse> res = List.of(res1, res2);

        return res;
    }
}