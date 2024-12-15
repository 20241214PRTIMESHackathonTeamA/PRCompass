package com.example.prcompass.controller.response;

import lombok.Data;
import java.util.List;

@Data
public class ChatResponseDTO {
    private List<Message> messages;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}

