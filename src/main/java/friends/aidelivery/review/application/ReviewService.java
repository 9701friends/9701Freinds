package friends.aidelivery.review.application;

import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.repository.ReviewRepository;
import friends.aidelivery.review.domain.vo.ReviewTime;
import friends.aidelivery.review.exception.ReviewAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(final ReviewCreateRequest request) {
        /*
        todo 유저, 주문으로 검증 로직 필요
        1. 유저 Repository 검증 -> 유저가 존재하는지
        2. 주문 Repository 검증 -> 주문이 존재하는지
        3. 주문 상태 검증 -> 주문이 완료되었는지
        4. 리뷰 Repository 검증 -> 이미 작성한 리뷰가 있는지
        5. 리뷰 비즈니스 로직 검증 -> 리뷰 작성기한이 유효한지
        6. 리뷰 저장
         */

        // todo 1번
        final UUID userId = UUID.randomUUID();

        // todo 2번
        final UUID orderId = request.orderId();
        final LocalDateTime orderTime = LocalDateTime.now().minusDays(3);

        // todo 3번
        final ReviewTime reviewTime = new ReviewTime(request.reviewTime(), orderTime);

        // 4번
        if (existsByOrderId(orderId)) {
            throw new ReviewAlreadyExistsException(orderId);
        }
        // 5번
        final Review review = new Review(orderId, userId, request.content(), request.rating(),
            reviewTime);
        // 6번
        final Review saved = reviewRepository.save(review);
        return ReviewResponse.of(saved);
    }

    private boolean existsByOrderId(final UUID orderId) {
        return reviewRepository.existsByOrderId(orderId);
    }
}
