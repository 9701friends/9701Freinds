package friends.aidelivery.review.application.dto.response;

import friends.aidelivery.review.domain.Review;
import java.util.List;
import org.springframework.data.domain.Page;

public record ReviewListResponse(List<ReviewResponse> reviewResponseList, int totalPages,
                                 long totalElements) {

    public static ReviewListResponse of(Page<Review> reviews) {
        return new ReviewListResponse(
            reviews.stream().map(ReviewResponse::of).toList(),
            reviews.getTotalPages(),
            reviews.getTotalElements()
        );
    }
}
