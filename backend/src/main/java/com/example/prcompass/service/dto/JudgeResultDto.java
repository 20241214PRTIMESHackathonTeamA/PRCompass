package com.example.prcompass.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

public class JudgeResultDto {

    @JsonProperty("evaluation")
    private String evaluation;

    // Lombokでgetter/setter, toStringなどを自動生成する
    @Data
    public static class Evaluation {
        private EvaluationDetail newsValue;
        private EvaluationDetail publicDecency;
        private EvaluationDetail legalCompliance;
    }

    @Data
    public static class EvaluationDetail {
        private boolean valid;
        private String reason;
    }

    // デシリアライズ用のメソッド
    public Evaluation getEvaluationDetails() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.evaluation, Evaluation.class);
    }

    // GetterとSetter
    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
