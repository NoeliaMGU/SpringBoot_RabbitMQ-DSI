package cat.tecnocampus.productService.application;

import cat.tecnocampus.productService.domain.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductController {
    ProductServicePersistence productServicePersistence;

    public ProductController(ProductServicePersistence productServicePersistence){
        this.productServicePersistence = productServicePersistence;
    }

    public Product createProduct(Product product) {
        System.out.println("name: " + product.getName() + " description: " + product.getDescription() + " weight: "+ product.getWeight());
        if(product.getId() == null){
            product.setId(UUID.randomUUID().toString());
        }
        return productServicePersistence.createProduct(product);
    }

    public void deleteProduct(String id){
        productServicePersistence.deleteProductService(id);
    }
}
