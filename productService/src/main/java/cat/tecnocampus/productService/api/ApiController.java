package cat.tecnocampus.productService.api;


import cat.tecnocampus.productService.application.ProductServicePersistence;
import cat.tecnocampus.productService.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {

    private ProductServicePersistence persistence;

    public ApiController(ProductServicePersistence persistence) {
        this.persistence = persistence;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return persistence.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id){
        return persistence.getProduct(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        System.out.println("name: " + product.getName() + " description: " + product.getDescription() + " weight: "+ product.getWeight());
        product.setId(UUID.randomUUID().toString());
        return persistence.createProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id){
        persistence.deleteProductService(id);
    }


}
