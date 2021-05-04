package cat.tecnocampus.productcomposite.messaging;


import cat.tecnocampus.productcomposite.application.Event;
import cat.tecnocampus.productcomposite.configuration.ProductSenderChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProductSenderChannels.class)
public class ProductSender {
    private MessageChannel productChannel;

    public ProductSender(ProductSenderChannels channels){
        this.productChannel = channels.productSenderChannel();
    }

    public void sendProduct(Event message){
        productChannel.send(MessageBuilder.withPayload(message).build());
        System.out.println("----ProductComposite.ProductSender.sendProduct()--aftersended");
    }
}
