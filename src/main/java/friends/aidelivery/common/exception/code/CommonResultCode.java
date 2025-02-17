package friends.aidelivery.common.exception.code;

public enum CommonResultCode {

    SUCCESS(200, "요청 성공"),

    BAD_REQUEST(400, "잘못된 요청입니다."),

    UNAUTHORIZED(401, "유효한 인증 정보가 아닙니다."),

    FORBIDDEN(403, "권한이 없습니다."),

    DATA_NOT_FOUNT(404, "데이터를 찾을 수 없습니다."),

    ERROR(500, "서버 에러");

    /**
     * 변수 설정
     */
    private final int code;
    private final String message;

    /**
     * 생성자 생성
     *
     * @param code    int
     * @param message String
     */
    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 오류 코드를 반환한다
     *
     * @return String
     */
    public int code() {
        return code;
    }

    /**
     * 오류 메시지를 반환한다 치환 문자("{}") 및 공백 문자 제거
     *
     * @return String
     */
    public String message() {
        return message.replace("{}", "").trim();
    }
}
