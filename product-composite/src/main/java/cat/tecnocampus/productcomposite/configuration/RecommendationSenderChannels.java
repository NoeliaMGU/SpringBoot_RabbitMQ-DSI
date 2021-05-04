package cat.tecnocampus.productcomposite.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RecommendationSenderChannels {
    //aqui s'ha de vincular amb el nom que li hem posat java al properties
    String RECOMMENDATION_CHANNEL = "recommendationSenderChannel";

    @Output(RecommendationSenderChannels.RECOMMENDATION_CHANNEL)
    MessageChannel recommendationSenderChannel();

}
