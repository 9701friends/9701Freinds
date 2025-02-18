package friends.aidelivery.store.infrastructure;

import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository jpaRepository;

    @Override
    public Store save(Store store) {
        return jpaRepository.save(store);
    }

    @Override
    public Optional<Store> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }

    @Override
    public Page<Store> findByName(String keyword, Pageable pageable) {
        return jpaRepository.findByNameContaining(keyword, pageable);
    }

}
