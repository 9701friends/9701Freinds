package friends.aidelivery.review.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.review.application.ReviewService;
import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.request.ReviewUpdateRequest;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> createReview(
        @Valid @RequestBody ReviewCreateRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PutMapping("{reviewId}")
    public ResponseEntity<CommonResponse> updateReview(
        @PathVariable UUID reviewId,
        @Valid @RequestBody ReviewUpdateRequest request) {
        ReviewResponse response = reviewService.updateReview(reviewId, request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<CommonResponse> softDeleteReview(
        @PathVariable UUID reviewId) {
        reviewService.softDeleteReview(reviewId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(),
            HttpStatus.OK);
    }

    @GetMapping("{reviewId}")
    public ResponseEntity<CommonResponse> getReviewInfo(
        @PathVariable UUID reviewId) {
        ReviewResponse response = reviewService.getReviewInfo(reviewId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }
}
