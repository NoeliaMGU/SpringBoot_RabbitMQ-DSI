package cat.tecnocampus.productService.domain;

public class ProductDoesNotExistException extends RuntimeException {
    public ProductDoesNotExistException(String id) {
        super("Product with id: " + id + " does not exist.");
    }
}
