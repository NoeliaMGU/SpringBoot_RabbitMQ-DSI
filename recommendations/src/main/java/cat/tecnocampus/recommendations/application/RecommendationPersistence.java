package cat.tecnocampus.recommendations.application;

import cat.tecnocampus.recommendations.domain.Recommendation;

import java.util.List;

public interface RecommendationPersistence {
    public List<Recommendation> getRecommendation(String productId);

    public void createRecommendation(Recommendation recommendation);

    public void deleteRecommendation(String productId);

}
