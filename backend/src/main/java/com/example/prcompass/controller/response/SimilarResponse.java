package com.example.prcompass.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimilarResponse {
    private String id;
    private String corporationName;
    private String publishdDatetime;
    private String title;
    private int like_count;
}

