package friends.aidelivery.review.domain.repository;

import friends.aidelivery.review.domain.Review;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {

    Review save(Review review);

    boolean existsByOrderHistoryId(UUID orderHistoryId);

    Optional<Review> findById(UUID reviewId);

    Page<Review> findAllByStoreId(UUID storeId, Pageable pageable);

    Page<Review> findAllByUserId(Long userId, Pageable pageable);
}
