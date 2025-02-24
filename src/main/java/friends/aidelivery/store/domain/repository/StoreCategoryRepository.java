package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.StoreCategory;
import java.util.Optional;
import java.util.UUID;

public interface StoreCategoryRepository {

    StoreCategory save(StoreCategory storeCategory);

    Optional<StoreCategory> findById(UUID uuid);

    void softDeleteCategory(UUID uuid);
}
