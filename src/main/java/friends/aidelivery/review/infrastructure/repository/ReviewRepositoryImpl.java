package friends.aidelivery.review.infrastructure.repository;

import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.repository.ReviewRepository;
import friends.aidelivery.review.infrastructure.jpa.ReviewJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    @Override
    public Review save(Review review) {
        return jpaRepository.save(review);
    }

    @Override
    public boolean existsByOrderId(UUID orderId) {
        return jpaRepository.existsByOrderId(orderId);
    }
}
