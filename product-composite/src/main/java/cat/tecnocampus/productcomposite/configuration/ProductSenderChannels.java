package cat.tecnocampus.productcomposite.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSenderChannels {
    //aqui s'ha de vincular amb el nom que li hem posat java al properties
    String PRODUCT_CHANNEL = "productSenderChannel";

    @Output(ProductSenderChannels.PRODUCT_CHANNEL)
    MessageChannel productSenderChannel();

}
