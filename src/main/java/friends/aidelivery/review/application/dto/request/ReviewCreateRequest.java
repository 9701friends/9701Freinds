package friends.aidelivery.review.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewCreateRequest(
    @NotBlank(message = "주문 식별자는 필수입니다.") UUID orderId,
    String content,
    @NotBlank(message = "평점은 필수입니다.") Integer rating,
    LocalDateTime reviewTime) {

}
