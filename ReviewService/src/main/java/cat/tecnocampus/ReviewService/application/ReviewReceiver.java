package cat.tecnocampus.ReviewService.application;

import cat.tecnocampus.ReviewService.configuration.ReviewReceiverChannels;
import cat.tecnocampus.ReviewService.domain.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ReviewReceiverChannels.class)
public class ReviewReceiver {
    private static Logger logger = LoggerFactory.getLogger(ReviewReceiver.class);
    private ReviewController reviewController;

    public ReviewReceiver(ReviewController reviewController){
        this.reviewController = reviewController;
    }

    @StreamListener(ReviewReceiverChannels.REVIEW_CHANNEL)
    public void reviewReceiver(Event payload) {
        System.out.println("----ReviewService.ReviewReceiver.reviewReceiver()");
        System.out.println(payload.toString());

        ObjectMapper mapper = new ObjectMapper();
        Review review = mapper.convertValue(payload.getData(), Review.class);

        System.out.println("\tproductId: " + review.getProductId());
        System.out.println("\tid: " + review.getId());
        System.out.println("\tauthor: " + review.getAuthor());
        System.out.println("\tsubject: " + review.getSubject());
        System.out.println("\tcontent: " + review.getContent());

        switch (payload.getEventType()) {
            case CREATE:
                reviewController.createReview(review);
                break;
            case DELETE:
                reviewController.deleteReview(review.getProductId());
                break;
            default:
                System.out.println("Event received is neither CREATE nor DELETE");
                break;
        }
    }
}
