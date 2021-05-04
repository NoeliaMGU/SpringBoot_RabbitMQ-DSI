package cat.tecnocampus.recommendations.application;

import cat.tecnocampus.recommendations.domain.Recommendation;
import cat.tecnocampus.recommendations.application.dao.RecommendationDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationController {
    public RecommendationPersistence recommendationPersistence;

    public ApplicationController(RecommendationPersistence recommendationPersistence) {
        this.recommendationPersistence = recommendationPersistence;
    }

    public List<Recommendation> getRecommendation(String productId) {
        return this.recommendationPersistence.getRecommendation(productId);
    }

    public void createRecommendation(Recommendation recommendation) {
        this.recommendationPersistence.createRecommendation(recommendation);
    }

    public void deleteRecommendation(String productId) {
        this.recommendationPersistence.deleteRecommendation(productId);
    }
}
