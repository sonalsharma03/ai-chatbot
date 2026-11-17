package com.example.chatbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/api/chat")
    public String chat() {
        return "AI Chatbot Backend is Running!";
    }

    @PostMapping("/api/chat")
    public String sendMessage(@RequestBody ChatRequest request) {
        return chatService.processMessage(request.getMessage());
    }
}