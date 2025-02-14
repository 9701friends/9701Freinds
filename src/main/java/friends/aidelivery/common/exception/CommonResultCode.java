package friends.aidelivery.common.exception;

public enum CommonResultCode {

    /**
     * 성공 처리
     */
    SUCCESS(200, "요청 성공"),

    /**
     * 로그인이 안 되어 있어 권한이 없는 경우
     */
    UNAUTHORIZED(401, "권한이 없습니다."),

    /**
     * 서버 에러
     */
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
