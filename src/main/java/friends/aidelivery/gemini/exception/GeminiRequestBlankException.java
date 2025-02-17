package friends.aidelivery.gemini.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class GeminiRequestBlankException extends CustomBadRequestException {

    public GeminiRequestBlankException() {
        super("요청 내용은 공백을 제외한 1자 이상이어야 합니다.");
    }
}
