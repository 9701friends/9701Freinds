package friends.aidelivery.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductCreateRequest(
    @NotBlank(message = "카테고리는 필수입니다.") UUID productCategoryId,
    @NotBlank(message = "상품 이름은 필수입니다.") String name,
    @NotBlank(message = "상품 설명은 필수입니다.") String content,
    @NotNull(message = "상품 가격은 필수입니다.") Long price) {

}
