package cat.tecnocampus.productcomposite.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ReviewSenderChannels {
    //aqui s'ha de vincular amb el nom que li hem posat java al properties
    String REVIEW_CHANNEL = "reviewSenderChannel";

    @Output(ReviewSenderChannels.REVIEW_CHANNEL)
    MessageChannel reviewSenderChannel();

}
