package spring.ai.example.spring_ai_demo.service;




import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String queryAi(String prompt) {
        return chatClient.call(prompt);

    }

    public String getCityGuide(String city, String interest) {
        var template = """
                I am a tourist visiting the city of {city}.
                I am mostly interested in {interest}.
                Tell me tips on what to do there.""";

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Map<String, Object> params = Map.of("city", city, "interest", interest);
        Prompt prompt = promptTemplate.create(params);

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    public String getDailyEatingRoutine(int height, int weight) {
        // Define the template with placeholders for height and weight
        String template = """
            My height is %d cm,\s
            My weight is %d kg,
            And give me a daily eating routine to maintain a healthy body and organism,
            and tell me the daily calories and other main nutrients.
           \s""";

        // Format the template with the provided height and weight
        String prompt = String.format(template, height, weight);

        // Assuming chatClient.call() accepts a String prompt directly and returns a result
        return chatClient.call(prompt);
    }

}
