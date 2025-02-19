package friends.aidelivery.review.domain.repository;

import friends.aidelivery.review.domain.Review;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository {

    Review save(Review review);

    boolean existsByOrderId(UUID orderId);

    Optional<Review> findById(UUID reviewId);
}
