package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import java.util.List;
import java.util.UUID;

public interface StoreCategoryMappingRepository {

    List<StoreCategoryMapping> findByStoreCategoryId(UUID storeCategoryId);
}
