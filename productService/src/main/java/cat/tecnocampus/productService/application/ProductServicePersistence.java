package cat.tecnocampus.productService.application;



import cat.tecnocampus.productService.domain.Product;

import java.util.List;

public interface ProductServicePersistence {
    public List<Product> getAllProducts();

    public Product createProduct(Product product);

    public Product getProduct(String id);

    public void deleteProductService(String id);
}
