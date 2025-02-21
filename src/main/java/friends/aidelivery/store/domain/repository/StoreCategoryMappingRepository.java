package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StoreCategoryMappingRepository {

    Page<Store> findStoresByCategoryId(UUID storeCategoryId, String sortBy, boolean isASC, Pageable pageable);
}
