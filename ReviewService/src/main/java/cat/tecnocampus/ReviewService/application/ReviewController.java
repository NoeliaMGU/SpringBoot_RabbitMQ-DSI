package cat.tecnocampus.ReviewService.application;

import cat.tecnocampus.ReviewService.domain.Review;
import org.springframework.stereotype.Component;


@Component
public class ReviewController {
    ReviewPersistence reviewPersistence;

    public ReviewController(ReviewPersistence reviewPersistence){
        this.reviewPersistence = reviewPersistence;
    }

    public void createReview(Review review){
        System.out.println("author: " + review.getAuthor() + " Subject: " + review.getSubject() +
                " Content: " + review.getContent());
        reviewPersistence.createReview(review);
    }

    public void deleteReview(String productId){
        reviewPersistence.deleteReview(productId);
    }
}
