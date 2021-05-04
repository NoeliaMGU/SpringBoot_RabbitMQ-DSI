package cat.tecnocampus.recommendations.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RecommendationReceiverChannel {
    String RECOMMENDATION_CHANNEL = "recommendationReceiverChannel";

    @Input(RECOMMENDATION_CHANNEL)
    SubscribableChannel recommendationReceiverChannels();
}
