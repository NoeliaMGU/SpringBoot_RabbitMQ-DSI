package cat.tecnocampus.productcomposite.messaging;


import cat.tecnocampus.productcomposite.application.Event;
import cat.tecnocampus.productcomposite.configuration.RecommendationSenderChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(RecommendationSenderChannels.class)
public class RecommendationSender {
    private MessageChannel recommendationChannel;

    public RecommendationSender(RecommendationSenderChannels channels){
        this.recommendationChannel = channels.recommendationSenderChannel();
    }

    public void sendRecommendation(Event message){
        recommendationChannel.send(MessageBuilder.withPayload(message).build());
        System.out.println("----ProductComposite.ProductSender.sendProduct()--aftersended");
    }
}
