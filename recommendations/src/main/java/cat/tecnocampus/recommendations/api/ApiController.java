package cat.tecnocampus.recommendations.api;

import cat.tecnocampus.recommendations.application.ApplicationController;
import cat.tecnocampus.recommendations.domain.Recommendation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private ApplicationController applicationController;

    public ApiController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @GetMapping("/recommendations/{productId}")
    public List<Recommendation> getRecommendations(@PathVariable String productId){
        return applicationController.getRecommendation(productId);
    }

    @PostMapping("/recommendations")
    public void createRecommendation(@RequestBody Recommendation recommendation) {
        System.out.println("name: " + recommendation.getId() + " for the product with ID: " + recommendation.getProductId());
        applicationController.createRecommendation(recommendation);
    }

    @DeleteMapping("/recommendations/{productId}")
    public void deleteRecommendation(@PathVariable String productId){
        applicationController.deleteRecommendation(productId);
    }
}
