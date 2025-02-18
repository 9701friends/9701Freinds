package friends.aidelivery.store.application.dto.response;

import friends.aidelivery.store.domain.StoreCategory;
import java.util.UUID;

public record StoreCategoryResponseDto(
    UUID categoryId,
    String name
) {

    public static StoreCategoryResponseDto of(StoreCategory storeCategory) {
        return new StoreCategoryResponseDto(storeCategory.getId(),
            storeCategory.getName().getValue());
    }
}
