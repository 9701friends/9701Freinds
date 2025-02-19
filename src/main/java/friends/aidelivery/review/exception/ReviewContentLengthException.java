package friends.aidelivery.review.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ReviewContentLengthException extends CustomBadRequestException {

    public ReviewContentLengthException(final int allowed, final String input) {
        super(String.format(
            "내용의 길이가 최대 길이를 초과했습니다. - 요청 정보 { 허용 값 : %d, 입력 값 : %d }",
            allowed,
            input.length()));
    }
}
