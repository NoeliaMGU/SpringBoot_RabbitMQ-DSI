package cat.tecnocampus.productService.persistence;

import cat.tecnocampus.productService.application.ProductServicePersistence;
import cat.tecnocampus.productService.domain.Product;
import cat.tecnocampus.productService.domain.ProductDoesNotExistException;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Persistence implements ProductServicePersistence {
    private JdbcTemplate jdbcTemplate;

    ResultSetExtractorImpl<Product> productsResultSetExtractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Product.class);

    RowMapperImpl<Product> productRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(Product.class);



    public Persistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        final var query = "select * from product";
        return jdbcTemplate.query(query, productsResultSetExtractor);
    }

    @Override
    public Product createProduct(Product product) {
        final var insertProduct = "INSERT INTO product (id, name, description, weight) VALUES (?,?,?,?)";
        jdbcTemplate.update(insertProduct, product.getId(), product.getName(), product.getDescription(), product.getWeight());
        return getProduct(product.getId());
    }

    @Override
    public Product getProduct(String id) {
        final var query = "select * from product where id=?";
        try {
            return jdbcTemplate.queryForObject(query, productRowMapper, id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ProductDoesNotExistException(id);
        }
    }

    @Override
    public void deleteProductService(String id) {
        final String deleteProduct = "DELETE FROM product where id=?";
        jdbcTemplate.update(deleteProduct, id);

    }
}
