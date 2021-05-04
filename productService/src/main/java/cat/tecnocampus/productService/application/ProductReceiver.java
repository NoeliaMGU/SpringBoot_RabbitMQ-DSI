package cat.tecnocampus.productService.application;

import cat.tecnocampus.productService.configuration.ProductReceiverChannels;
import cat.tecnocampus.productService.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(ProductReceiverChannels.class)
public class ProductReceiver {

    private static Logger logger = LoggerFactory.getLogger(ProductReceiver.class);
    private ProductController productController;

    public ProductReceiver(ProductController productController){
        this.productController = productController;
    }

    @StreamListener(ProductReceiverChannels.PRODUCT_CHANNEL)
    public void productReceiver(Event payload) {
        System.out.println("----ProductService.ProductReceiver.productReceiver()");
        System.out.println(payload.toString());
        //logger.info(payload.toString());

        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.convertValue(payload.getData(), Product.class);

        System.out.println("\tid: " + product.getId());
        System.out.println("\tname: " + product.getName());
        System.out.println("\tdescription: " + product.getDescription());
        System.out.println("\tweight: " + product.getWeight());

        switch(payload.getEventType()){
            case CREATE:
                productController.createProduct(product);
                break;
            case DELETE:
                productController.deleteProduct(product.getId());
                break;
            default:
                System.out.println("Event received is neither CREATE nor DELETE");
                break;
        }
        //aqui rebem l'event amb la peticio i hem de cridar a api controller el metode que toqui

    }
}