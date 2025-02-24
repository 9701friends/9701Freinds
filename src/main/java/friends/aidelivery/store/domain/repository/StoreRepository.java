package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.Store;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepository {
    Store save(Store store);

    Optional<Store> findById(UUID uuid);

    Page<Store> findByName(String keyword, Pageable pageable);

    List<Store> findAllActiveStores();

    List<Store> findAllDeactiveStores();

    void softDeleteStore(UUID storeId);
}
