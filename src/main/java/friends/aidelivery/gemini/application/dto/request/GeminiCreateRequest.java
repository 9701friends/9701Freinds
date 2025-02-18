package friends.aidelivery.gemini.application.dto.request;

import friends.aidelivery.gemini.domain.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GeminiCreateRequest(
    @NotBlank(message = "요청 내용은 필수입니다.") String content,
    @NotNull(message = "요청 유형은 필수입니다.") RequestType requestType) {

}
