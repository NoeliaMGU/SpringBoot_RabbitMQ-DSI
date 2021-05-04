package cat.tecnocampus.productcomposite.api;

import cat.tecnocampus.productcomposite.application.ApplicationController;
import cat.tecnocampus.productcomposite.domain.ProductComposite;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ApiController {
    private ApplicationController applicationController;

    public ApiController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @GetMapping("/products")
    public List<ProductComposite> getProducts() {
        System.out.println("GETTING ALL PRODUCTS");
        return applicationController.getProductsCompositeCircuitBreaker();
    }

    @GetMapping("/products/{id}")
    public ProductComposite getProduct(@PathVariable String id) {
        return applicationController.getProductsCompositeCircuitBreaker(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        applicationController.deleteProductComposite(id);
    }

    @PostMapping("/products")
    public void createProduct(@RequestBody ProductComposite productComposite) {
        System.out.println("----ProductComposite.ApiController-name: " + productComposite.getProduct().getName());
        applicationController.createProductComposite(productComposite);
    }

    private void simulateDelay(int delay){
        System.out.println("Sleeping for " + delay + " seconds...");
        try{
            Thread.sleep(delay * 1000);
        }catch(InterruptedException e){
            System.out.println("Moving on...");
        }
    }

    private void throwErrorIfBadLuck(int faultRatio){
        int randomThreshold = getRandomNumber(1, 100);
        if(faultRatio < randomThreshold){
            System.out.println("We got lucky, no error ocurred...");
        }else{
            System.out.println("Bad luck, an error occured... " + faultRatio);
            throw new RuntimeException("Something went wrong");
        }
    }

    private final Random randomNumberGenerator = new Random();
    private int getRandomNumber(int min, int max){
        if(max < min){
            throw new RuntimeException("Max must be greater than min");
        }
        return  randomNumberGenerator.nextInt(((max - min) + 1) + min);
    }
}
