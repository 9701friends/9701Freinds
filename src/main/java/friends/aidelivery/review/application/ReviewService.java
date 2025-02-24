package friends.aidelivery.review.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.order.application.OrderHistoryService;
import friends.aidelivery.order.domain.OrderHistory;
import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.request.ReviewUpdateRequest;
import friends.aidelivery.review.application.dto.response.ReviewListResponse;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.repository.ReviewRepository;
import friends.aidelivery.review.exception.ReviewAlreadyExistsException;
import friends.aidelivery.review.exception.ReviewForbiddenException;
import friends.aidelivery.review.exception.ReviewNotFoundException;
import friends.aidelivery.store.application.StoreService;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final UserService userService;
    private final OrderHistoryService orderHistoryService;
    private final StoreService storeService;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponse createReview(final UUID orderHistoryId, final ReviewCreateRequest request,
        final UserDetailsImpl userDetails) {

        final Long userId = userDetails.getUserId();
        final User user = userService.getUserOrElseThrow(userId);
        final OrderHistory orderHistory = orderHistoryService.getOrderHistoryOrElseThrow(
            orderHistoryId);

        validateUser(orderHistory.getUserId(), userId);
        checkIfReviewExists(orderHistoryId);

        final Review review = new Review(orderHistory, user, request.content(), request.rating(),
            request.reviewTime());
        final Review saved = reviewRepository.save(review);

        storeService.calculateRating(saved.getStoreId(), 1, BigDecimal.ZERO,
            review.getRating().getBigDecimalValue());

        return ReviewResponse.of(saved);
    }

    private void validateUser(Long requestId, Long expectedId) {
        if (!requestId.equals(expectedId)) {
            throw new ReviewForbiddenException();
        }
    }

    private void checkIfReviewExists(final UUID orderHistoryId) {
        if (reviewRepository.existsByOrderHistoryId(orderHistoryId)) {
            throw new ReviewAlreadyExistsException(orderHistoryId);
        }
    }

    @Transactional
    public ReviewResponse updateReview(final UUID reviewId, final ReviewUpdateRequest request,
        final UserDetailsImpl userDetails) {
        final Review review = getReviewOrElseThrow(reviewId);
        validateUser(review.getUser().getId(), userDetails.getUserId());

        final BigDecimal oldRating = review.getRating().getBigDecimalValue();
        review.update(request.content(), request.rating(), request.reviewTime());
        final BigDecimal newRating = review.getRating().getBigDecimalValue();

        storeService.calculateRating(review.getStoreId(), 0, oldRating, newRating);
        return ReviewResponse.of(review);
    }

    @Transactional
    public void softDeleteReview(final UUID reviewId, final UserDetailsImpl userDetails) {
        final User user = userService.getUserOrElseThrow(userDetails.getUserId());
        final UserRoleEnum userRole = user.getRole();

        final Review review = getReviewOrElseThrow(reviewId);
        review.softDelete();

        storeService.calculateRating(review.getStoreId(), -1,
            review.getRating().getBigDecimalValue(),
            BigDecimal.ZERO);
    }

    public ReviewResponse getReviewInfo(final UUID reviewId) {
        return ReviewResponse.of(getReviewOrElseThrow(reviewId));
    }

    public Review getReviewOrElseThrow(final UUID reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    public ReviewListResponse getReviewsByStoreId(final UUID storeId, final int page,
        final int size,
        final String sortBy, final boolean isAsc) {
        storeService.getStoreOrElseThrow(storeId);
        final Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Review> reviews = reviewRepository.findAllByStoreId(storeId, pageable);
        return ReviewListResponse.of(reviews);
    }

    public ReviewListResponse getReviewsByUserId(final Long userId, final int page, final int size,
        final String sortBy, final boolean isAsc) {
        userService.getUserOrElseThrow(userId);
        final Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Review> reviews = reviewRepository.findAllByUserId(userId, pageable);
        return ReviewListResponse.of(reviews);
    }

    private Pageable createPageable(final int page, final int size,
        final String sortBy, final boolean isAsc) {
        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, size, sort);
    }

}
