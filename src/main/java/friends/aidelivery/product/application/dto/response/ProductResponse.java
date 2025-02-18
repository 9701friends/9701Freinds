package friends.aidelivery.product.application.dto.response;

import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.enums.ProductStatus;
import java.util.UUID;

public record ProductResponse(
    UUID productId,
    UUID productCategoryId,
    String name,
    String content,
    Long price,
    ProductStatus status) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getProductCategory().getId(),
            product.getName().getValue(),
            product.getContent().getValue(),
            product.getPrice().getValue(),
            product.getStatus());
    }
}
