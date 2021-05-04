package cat.tecnocampus.productcomposite.messaging;

import cat.tecnocampus.productcomposite.application.Event;
import cat.tecnocampus.productcomposite.configuration.ReviewSenderChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReviewSenderChannels.class)
public class ReviewSender {
    private MessageChannel reviewChannel;

    public ReviewSender(ReviewSenderChannels channels){
        this.reviewChannel = channels.reviewSenderChannel();
    }

    public void sendReview(Event message){
        reviewChannel.send(MessageBuilder.withPayload(message).build());
        System.out.println("----ProductComposite.ProductSender.sendProduct()--aftersended");
    }
}
