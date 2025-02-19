package friends.aidelivery.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ProductCategoryUpdateRequest(
    @NotBlank(message = "가게는 필수입니다.") UUID storeId,
    @NotBlank(message = "카테고리 이름은 필수입니다.") String name,
    @NotBlank(message = "카테고리 설명은 필수입니다.") String content
) {

}
