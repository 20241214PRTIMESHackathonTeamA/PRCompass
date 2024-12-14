package com.example.prcompass.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendResponse {
    private List<String> newsTopics;
}
