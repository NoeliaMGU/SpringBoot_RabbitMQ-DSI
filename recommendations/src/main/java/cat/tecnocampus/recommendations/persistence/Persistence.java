package cat.tecnocampus.recommendations.persistence;

import cat.tecnocampus.recommendations.application.RecommendationPersistence;
import cat.tecnocampus.recommendations.application.dao.RecommendationDAO;
import cat.tecnocampus.recommendations.domain.Recommendation;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Persistence implements RecommendationPersistence {
    private JdbcTemplate jdbcTemplate;

    public Persistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<Recommendation> recommendationsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Recommendation.class);

    RowMapperImpl<Recommendation> recommendationRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(Recommendation.class);

    @Override
    public List<Recommendation> getRecommendation(String productId) {
        final var query = "SELECT * FROM recommendation WHERE productId = ?";
        return jdbcTemplate.query(query, recommendationsRowMapper,  productId);
    }

    @Override
    public void createRecommendation(Recommendation recommendation) {
        final var query = "INSERT INTO recommendation (productId, author, rate, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, recommendation.getProductId(), recommendation.getAuthor(), recommendation.getRate(), recommendation.getContent());
    }

    @Override
    public void deleteRecommendation(String productId) {
        final var query = "DELETE FROM recommendation WHERE productId = ?";
        jdbcTemplate.update(query, productId);
    }
}
