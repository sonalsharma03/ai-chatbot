package com.example.chatbot;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final GeminiService geminiService;

    public ChatService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String processMessage(String message) {
        return geminiService.generateResponse(message);
    }
}