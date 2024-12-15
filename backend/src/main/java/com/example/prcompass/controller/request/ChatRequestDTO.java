package com.example.prcompass.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequestDTO {
    private List<Message> messages;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}