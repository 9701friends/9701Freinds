package friends.aidelivery.review.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewTimeException extends CustomBadRequestException {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss");

    public ReviewTimeException(final LocalDateTime allowed, final LocalDateTime input) {
        super(String.format(
            "리뷰를 작성할 수 있는 기간이 지났습니다. - 요청 정보 { 최대 작성 가능 시 : %s, 입력 시간 : %s }",
            allowed.format(FORMATTER),
            input.format(FORMATTER)));
    }
}
