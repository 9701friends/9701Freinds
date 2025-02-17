package friends.aidelivery.common.application.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 공통 응답 필드
 */
@Getter
@Builder
public class CommonResponse {

    private int code;
    private String message;
    private Object results;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(int code, String message, Object results) {
        this.code = code;
        this.message = message;
        this.results = results;
    }
}