package cat.tecnocampus.productService.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductReceiverChannels {
    //aqui s'ha de vincular amb el nom que li hem posat java al properties
    String PRODUCT_CHANNEL = "productReceiverChannel";

    @Input(PRODUCT_CHANNEL)
    SubscribableChannel productReceiverChannel();

}
