package cat.tecnocampus.ReviewService.api;

import cat.tecnocampus.ReviewService.application.ReviewPersistence;
import cat.tecnocampus.ReviewService.domain.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private ReviewPersistence persistence;

    public ApiController(ReviewPersistence persistence) {
        this.persistence = persistence;
    }

    @GetMapping("/review/{id}")
    public List<Review> getReview(@PathVariable String id) {
        System.out.println("-----------GET A REVIEW");
        return persistence.getReviews(id);
    }


    @GetMapping("/review")
    public List<Review> getReviews() {
        return persistence.getReviews();
    }

    @PostMapping("/review")
    public void createReview(@RequestBody Review review) {
        System.out.println("author: " + review.getAuthor() + " Subject: " + review.getSubject() + " Content: " + review.getContent());
        persistence.createReview(review);
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable String id){
        persistence.deleteReview(id);
    }
}
