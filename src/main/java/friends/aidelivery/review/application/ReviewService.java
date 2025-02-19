package friends.aidelivery.review.application;

import friends.aidelivery.order.application.OrderService;
import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.repository.OrderRepository;
import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.request.ReviewUpdateRequest;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.repository.ReviewRepository;
import friends.aidelivery.review.exception.ReviewAlreadyExistsException;
import friends.aidelivery.review.exception.ReviewNotFoundException;
import friends.aidelivery.user.domain.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Transactional
    public ReviewResponse createReview(final ReviewCreateRequest request) {
        /*
        todo 유저, 주문으로 검증 로직 필요
        1. 유저 Repository 검증 -> 유저가 존재하는지
        2. 주문 Repository 검증 -> 주문이 존재하는지, 주문이 완료되었는지
        3. 리뷰 Repository 검증 -> 이미 작성한 리뷰가 있는지
        4. 리뷰 비즈니스 로직 검증 -> 리뷰 작성기한이 유효한지
        5. 리뷰 저장
         */

        // todo 1번 유저 검증
        final String email = "email@email.com";
        final User user = null;

        // 2. 주문 도메인에 검증 위임
        final UUID orderId = request.orderId();
        final Order order = orderService.validateOrderForReview(orderId);

        // 3. 이미 작성한 리뷰있는지 검증
        checkIfReviewExists(orderId);

        // 4. 리뷰 도메인에 검증 위임
        final Review review = new Review(order, user, request.content(), request.rating(),
            request.reviewTime());

        // 5. 리뷰 저장
        final Review saved = reviewRepository.save(review);
        return ReviewResponse.of(saved);
    }

    private void checkIfReviewExists(final UUID orderId) {
        if (existsByOrderId(orderId)) {
            throw new ReviewAlreadyExistsException(orderId);
        }
    }

    public boolean existsByOrderId(final UUID orderId) {
        return reviewRepository.existsByOrderId(orderId);
    }

    @Transactional
    public ReviewResponse updateReview(final UUID reviewId, final ReviewUpdateRequest request) {
        final Review review = getReviewById(reviewId);
        review.update(request.content(), request.rating(), request.reviewTime());
        return ReviewResponse.of(review);
    }

    @Transactional
    public void softDeleteReview(UUID reviewId) {
        // todo 유저 권한 검증 -> 마스터만 삭제 가능
        final Review review = getReviewById(reviewId);
        review.softDelete();
    }

    public ReviewResponse getReviewInfo(final UUID reviewId) {
        return ReviewResponse.of(getReviewById(reviewId));
    }

    public Review getReviewById(final UUID reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }


}
