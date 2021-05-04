package cat.tecnocampus.ReviewService.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReviewReceiverChannels {
    String REVIEW_CHANNEL = "reviewReceiverChannel";

    @Input(REVIEW_CHANNEL)
    SubscribableChannel reviewReceiverChannel();
}
