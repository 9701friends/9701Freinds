package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreRegionMapping;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRegionMappingRepository {

    Page<Store> findStoresByRegionId(UUID regionUUID, String sortBy, boolean isAsc, Pageable pageable);
}
