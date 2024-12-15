package com.example.prcompass.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimilarResponse {
    private String corporationName;
    private String publishdDatetime;
    private String thumbnailUrl;
    private String postUrl;
    private String title;
    private int likeCount;
}

