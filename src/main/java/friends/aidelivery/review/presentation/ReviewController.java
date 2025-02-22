package friends.aidelivery.review.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.review.application.ReviewService;
import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.request.ReviewUpdateRequest;
import friends.aidelivery.review.application.dto.response.ReviewListResponse;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import friends.aidelivery.user.domain.enums.UserRoleEnum.Authority;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Secured({Authority.CUSTOMER, Authority.MANAGER, Authority.MASTER})
    @PostMapping("/{orderHistoryId}")
    public ResponseEntity<CommonResponse> createReview(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderHistoryId,
        @Valid @RequestBody ReviewCreateRequest request) {
        ReviewResponse response = reviewService.createReview(orderHistoryId, request, userDetails);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @Secured({Authority.CUSTOMER, Authority.MANAGER, Authority.MASTER})
    @PutMapping("/{reviewId}")
    public ResponseEntity<CommonResponse> updateReview(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID reviewId,
        @Valid @RequestBody ReviewUpdateRequest request) {
        ReviewResponse response = reviewService.updateReview(reviewId, request, userDetails);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @Secured({Authority.MANAGER, Authority.MASTER})
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponse> softDeleteReview(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID reviewId) {
        reviewService.softDeleteReview(reviewId, userDetails);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(),
            HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<CommonResponse> getReviewInfo(
        @PathVariable UUID reviewId) {
        ReviewResponse response = reviewService.getReviewInfo(reviewId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<CommonResponse> getReviewsByStoreId(
        @PathVariable UUID storeId,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc) {
        ReviewListResponse response = reviewService.getReviewsByStoreId(storeId, page, size, sortBy,
            isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse> getReviewsByUserId(
        @PathVariable Long userId,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc) {
        ReviewListResponse response = reviewService.getReviewsByUserId(userId, page, size, sortBy,
            isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }
}
