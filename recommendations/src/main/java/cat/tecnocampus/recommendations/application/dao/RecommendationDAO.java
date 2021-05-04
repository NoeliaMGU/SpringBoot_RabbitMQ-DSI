package cat.tecnocampus.recommendations.application.dao;

import cat.tecnocampus.recommendations.domain.Recommendation;

import java.util.List;

public interface RecommendationDAO {

    public List<Recommendation> getRecommendation(String productId);

    public void createEecommendation(Recommendation recommendation);

    public void deleteRecommendation(String productId);
}
