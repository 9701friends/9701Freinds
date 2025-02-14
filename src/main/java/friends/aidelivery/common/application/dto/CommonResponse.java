package friends.aidelivery.common.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 공통 응답 필드
 */
@Getter
@Builder
public class CommonResponse {

    private int code;
    private String message;

    @Nullable
    private Object results;

}
