package friends.aidelivery.review.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ReviewRatingMinException extends CustomBadRequestException {

    public ReviewRatingMinException(final Integer allowed, final Integer input) {
        super(String.format(
            "평점이 최소 평점보다 낮습니다. - 요청 정보 { 최저 평점 : %d, 입력 값 : %d }",
            allowed,
            input));
    }
}
