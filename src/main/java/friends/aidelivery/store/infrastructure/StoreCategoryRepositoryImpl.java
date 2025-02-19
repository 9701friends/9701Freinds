package friends.aidelivery.store.infrastructure;

import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.repository.StoreCategoryRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreCategoryJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreCategoryRepositoryImpl implements StoreCategoryRepository {

    private final StoreCategoryJpaRepository jpaRepository;


    @Override
    public StoreCategory save(StoreCategory storeCategory) {
        return jpaRepository.save(storeCategory);
    }

    @Override
    public Optional<StoreCategory> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }
}
