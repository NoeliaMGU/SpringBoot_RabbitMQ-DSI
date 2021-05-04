package cat.tecnocampus.ReviewService.application;

import cat.tecnocampus.ReviewService.domain.Review;

import java.util.List;

public interface ReviewPersistence {
    List<Review> getReviews(String id);

    void createReview(Review product);

    void deleteReview(String id);

    List<Review> getReviews();
}
