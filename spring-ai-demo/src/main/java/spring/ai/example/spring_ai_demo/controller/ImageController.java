package spring.ai.example.spring_ai_demo.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.ai.example.spring_ai_demo.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("generate-image")
    public ResponseEntity<?> generateImage(HttpServletResponse response, @RequestParam("prompt") String prompt) throws IOException {
        // Validate prompt
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt cannot be null or empty");
        }

        // Generate the image
        ImageResponse imageResponse = imageService.generateImage(prompt);

        // Check if the image generation was successful and URL is present
        String imageUrl = null;
        if (imageResponse != null && imageResponse.getResult() != null &&
                imageResponse.getResult().getOutput() != null) {
            imageUrl = imageResponse.getResult().getOutput().getUrl();
        }

        // Redirect to the image URL if available
        if (imageUrl != null) {
            response.sendRedirect(imageUrl);
            return null; // Since redirection occurs, no further response body is needed
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image generation failed or URL not available");
        }
    }
}
