package cat.tecnocampus.productcomposite.application;

import cat.tecnocampus.productcomposite.domain.Product;
import cat.tecnocampus.productcomposite.domain.ProductComposite;
import cat.tecnocampus.productcomposite.domain.Recommendation;
import cat.tecnocampus.productcomposite.domain.Review;
import cat.tecnocampus.productcomposite.messaging.ProductSender;
import cat.tecnocampus.productcomposite.messaging.RecommendationSender;
import cat.tecnocampus.productcomposite.messaging.ReviewSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationController {
    private RestTemplate restTemplate;
    private String productServiceUrl;
    private String recommendationServiceUrl;
    private String reviewServiceUrl;
    private ProductSender productSender;
    private ReviewSender reviewSender;
    private RecommendationSender recommendationSender;
    private CircuitBreakerFactory circuitBreakerFactory;


    public ApplicationController(RestTemplate restTemplate,
                                 @Value("${app.product-service.host}") String productServiceHost,
                                 //@Value("${app.product-service.port}") int productServicePort,
                                 @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                                 //@Value("${app.recommendation-service.port}") int recommendationServicePort,
                                 @Value("${app.review-service.host}") String reviewServiceHost,
                                 //@Value("${app.review-service.port}") int reviewServicePort,
                                 ProductSender productSender, ReviewSender reviewSender,
                                 RecommendationSender recommendationSender,
                                 CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        productServiceUrl = "http://" + productServiceHost + "/products";
        recommendationServiceUrl = "http://" + recommendationServiceHost + "/recommendations";
        reviewServiceUrl = "http://" + reviewServiceHost + "/review";
        this.productSender = productSender;
        this.reviewSender = reviewSender;
        this.recommendationSender = recommendationSender;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public void createProductComposite(ProductComposite productComposite){
        Product product = productComposite.getProduct();
        product.setId(UUID.randomUUID().toString());
        List<Recommendation> recommendations = productComposite.getRecommendations();
        List<Review> reviews = productComposite.getReviews();


        Event event = new Event(Event.Type.CREATE, productComposite.getProduct().getId(), productComposite.getProduct());
        productSender.sendProduct(event);


        //product = restTemplate.postForObject(productServiceUrl, product, Product.class);
        for(Recommendation r: recommendations){
            r.setProductId(product.getId());
            event = new Event(Event.Type.CREATE, r.getProductId(), r);
            recommendationSender.sendRecommendation(event);
            //restTemplate.postForObject(recommendationServiceUrl, r, String.class);
        }
        for(Review r: reviews){
            r.setProductId(product.getId());
            event = new Event(Event.Type.CREATE, r.getProductId(), r);
            reviewSender.sendReview(event);
            //restTemplate.postForObject(reviewServiceUrl, r, String.class);
        }
    }

    public List<ProductComposite> getProductsCompositeCircuitBreaker(){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("product");
        List<ProductComposite> productComposites = new ArrayList<ProductComposite>();

        List<Product> products = circuitBreaker.run(
                () -> getAllProducts(),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return new ArrayList<Product>();});

        if(!products.isEmpty()){
            List<Recommendation> recommendations; //= new ArrayList<Recommendation>();
            List<Review> reviews; //= new ArrayList<Review>();
            ProductComposite composite;

            for(Product p: products){
                composite = new ProductComposite();
                recommendations = circuitBreaker.run(
                        () -> getRecommendations(p.getId()),
                        throwable -> {
                            System.out.println(throwable.getMessage());
                            return new ArrayList<Recommendation>();});

                composite.setRecommendations(recommendations);

                reviews = circuitBreaker.run(
                        () -> getReviews(p.getId()),
                        throwable -> {
                            System.out.println(throwable.getMessage());
                            return new ArrayList<Review>();});

                composite.setReviews(reviews);
                composite.setProduct(p);

                productComposites.add(composite);
            }
        }else{
            //products ha caigut i no tenim els id per fer les peticions
            var errorComposite = new ProductComposite();
            productComposites.add(errorComposite);
        }

        return productComposites;
    }

    public ProductComposite getProductsCompositeCircuitBreaker(String id){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("product");

        ProductComposite composite = new ProductComposite();

        Product product = circuitBreaker.run(
                () -> getProduct(id),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return new Product();});

        List<Recommendation> recommendations = circuitBreaker.run(
                () -> getRecommendations(id),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return new ArrayList<Recommendation>();});

        List<Review> reviews = circuitBreaker.run(
                () -> getReviews(id),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return new ArrayList<Review>();});

        composite.setProduct(product);
        composite.setRecommendations(recommendations);
        composite.setReviews(reviews);

        return composite;
    }

    private List<Product> getAllProducts(){
        var products =restTemplate.exchange(productServiceUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Product>>() {});
        return products.getBody();
    }

    private Product getProduct(String productId){
        var product =restTemplate.exchange(productServiceUrl + "/" + productId, HttpMethod.GET,
                null, new ParameterizedTypeReference<Product>(){});
        return product.getBody();
    }

    private List<Recommendation> getRecommendations(String productId){
        var recommendations = restTemplate.exchange(recommendationServiceUrl + "/" + productId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {});
        return recommendations.getBody();
    }

    private List<Review> getReviews(String productId){
        var reviews = restTemplate.exchange(reviewServiceUrl + "/" + productId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {});
        return reviews.getBody();
    }

    public void deleteProductComposite(String id) {
        Event event = new Event(Event.Type.DELETE, id, id);
        productSender.sendProduct(event);
        reviewSender.sendReview(event);
        recommendationSender.sendRecommendation(event);
        //var product = restTemplate.exchange(productServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Product>>() {});
        //var recommendation = restTemplate.exchange(recommendationServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Recommendation>>() {});
        //var review = restTemplate.exchange(reviewServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Review>>() {});
    }

    public List<ProductComposite> getProductsComposite(){
        var products = restTemplate.exchange(productServiceUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Product>>() {});

        List<ProductComposite> productComposites = new LinkedList<ProductComposite>();

        for(Product p: products.getBody()){
            var recommendation = restTemplate.exchange(recommendationServiceUrl + "/" + p.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {});
            var review = restTemplate.exchange(reviewServiceUrl + "/" + p.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {});
            productComposites.add(new ProductComposite(p, review.getBody(), recommendation.getBody()));
        }

        return  productComposites;
    }

    public ProductComposite getProductsComposite(String id){
        var product = restTemplate.exchange(productServiceUrl + "/" + id, HttpMethod.GET,
                null, new ParameterizedTypeReference<Product>() {});

        var recommendation= restTemplate.exchange(recommendationServiceUrl + "/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {});

        var review = restTemplate.exchange(reviewServiceUrl + "/" + id, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Review>>() {});

        return new ProductComposite(product.getBody(), review.getBody(), recommendation.getBody());
    }


}