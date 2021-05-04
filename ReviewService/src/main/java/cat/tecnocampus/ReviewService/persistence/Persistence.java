package cat.tecnocampus.ReviewService.persistence;

import cat.tecnocampus.ReviewService.application.ReviewPersistence;
import cat.tecnocampus.ReviewService.domain.Review;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Persistence implements ReviewPersistence {
    private JdbcTemplate jdbcTemplate;

    ResultSetExtractorImpl<Review> productResultSetExtractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Review.class);

    public Persistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Review> getReviews(String id) {
        final var query = "select * from review where productId = ?";
        return jdbcTemplate.query(query, productResultSetExtractor,  id);
    }

    @Override
    public void createReview(Review review) {
        final var insertReview = "INSERT INTO review (productId, author, subject, content) VALUES (?, ?,?,?)";
        jdbcTemplate.update(insertReview, review.getProductId(), review.getAuthor(), review.getSubject(), review.getContent());
    }

    @Override
    public void deleteReview(String id) {
        final String deleteProduct = "DELETE FROM review where productId=?";
        jdbcTemplate.update(deleteProduct, id);
    }

    @Override
    public List<Review> getReviews() {
        final var query = "select * from review";
        return jdbcTemplate.query(query, productResultSetExtractor);
    }
}
