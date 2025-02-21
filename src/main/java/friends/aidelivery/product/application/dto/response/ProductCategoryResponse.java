package friends.aidelivery.product.application.dto.response;

import friends.aidelivery.product.domain.ProductCategory;
import java.util.List;
import java.util.UUID;

public record ProductCategoryResponse(
    UUID productCategoryId,
    UUID storeId,
    String name,
    String content,
    List<ProductResponse> products) {

    public static ProductCategoryResponse of(final ProductCategory productCategory) {
        return new ProductCategoryResponse(
            productCategory.getId(),
            productCategory.getStore().getId(),
            productCategory.getName().getValue(),
            productCategory.getContent().getValue(),
            productCategory.getProducts().stream().map(ProductResponse::of).toList()
        );
    }
}
