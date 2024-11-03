package spring.ai.example.spring_ai_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.ai.example.spring_ai_demo.Service.ChatService;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/prompt")
    public String prompt(@RequestParam String message) {
        return chatService.callChatGPT(message);
    }
}
