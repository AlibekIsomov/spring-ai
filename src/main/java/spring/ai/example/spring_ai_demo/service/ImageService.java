package spring.ai.example.spring_ai_demo.service;

import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageClient imageClient;

    public ImageService(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    public ImageResponse generateImage(String prompt) {
        OpenAiImageOptions openAiImageOptions = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1) //Number of images to be generated
                .withHeight(1024)
                .withWidth(1024)
                .build();

        return imageClient.call(new ImagePrompt(prompt, openAiImageOptions));
    }
}
