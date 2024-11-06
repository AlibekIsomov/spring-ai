package spring.ai.example.spring_ai_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.ai.example.spring_ai_demo.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/text")
    public ResponseEntity<String> prompt(@RequestParam String prompt) {
        String response = chatService.queryAi(prompt);
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt cannot be null or empty");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("city-guide")
    public ResponseEntity<String> cityGuide(@RequestParam("city") String city, @RequestParam("interest") String interest) {
        if (city == null || interest == null){
            return ResponseEntity.badRequest().body("Prompt cannot be null or empty");
        }
        String response = chatService.getCityGuide(city, interest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("daily-eating-routine")
    public ResponseEntity<String> dailyEatingRoutine(@RequestParam("height") int height, @RequestParam("weight") int weight) {
        if(height == 0 || weight == 0){
            return ResponseEntity.badRequest().body("Height and weight cannot be null");
        }
        String response = chatService.getDailyEatingRoutine(height, weight);
        return ResponseEntity.ok(response);
    }
}
