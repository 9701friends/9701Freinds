package friends.aidelivery.product.application.dto.request;

import friends.aidelivery.product.domain.enums.ProductStatus;
import java.util.List;
import java.util.UUID;

public record ProductStatusUpdateRequest(List<UUID> productIds, ProductStatus status) {

}
