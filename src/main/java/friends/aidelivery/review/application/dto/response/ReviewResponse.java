package friends.aidelivery.review.application.dto.response;

import friends.aidelivery.review.domain.Review;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
    UUID reviewId,
    UUID orderId,
    String email,
    String content,
    Integer rating,
    LocalDateTime reviewTime) {

    public static ReviewResponse of(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getOrder().getId(),
            review.getUser().getEmail().getValue(),
            review.getContent().getValue(),
            review.getRating().getValue(),
            review.getReviewTime().getValue()
        );
    }
}
