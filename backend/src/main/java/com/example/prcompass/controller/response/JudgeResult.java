package com.example.prcompass.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JudgeResult {
    private ValidationResult newsValue;
    private ValidationResult publicDecency;
    private ValidationResult legalCompliance;

    @AllArgsConstructor
    @Data
    public static class ValidationResult {
        private boolean isValid;
        private String reason;
    }
}
