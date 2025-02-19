package friends.aidelivery.review.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ReviewRatingMaxException extends CustomBadRequestException {

    public ReviewRatingMaxException(final Integer allowed, final Integer input) {
        super(String.format(
            "평점이 최대 평점보다 높습니다. - 요청 정보 { 최대 평점 : %d, 입력 값 : %d }",
            allowed,
            input));
    }
}
