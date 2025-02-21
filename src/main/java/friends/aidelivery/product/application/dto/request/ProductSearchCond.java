package friends.aidelivery.product.application.dto.request;

import friends.aidelivery.product.domain.enums.ProductStatus;
import java.util.UUID;

public record ProductSearchCond(UUID storeId, UUID productCategoryId, ProductStatus status,
                                boolean isDeleted) {

}
