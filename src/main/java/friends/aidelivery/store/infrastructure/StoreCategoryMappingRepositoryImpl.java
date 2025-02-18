package friends.aidelivery.store.infrastructure;

import friends.aidelivery.store.domain.StoreCategoryMapping;
import friends.aidelivery.store.domain.repository.StoreCategoryMappingRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreCategoryMappingJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreCategoryMappingRepositoryImpl implements StoreCategoryMappingRepository {

    private final StoreCategoryMappingJpaRepository jpaRepository;


    @Override
    public List<StoreCategoryMapping> findByStoreCategoryId(UUID storeCategoryId) {
        return jpaRepository.findByStoreCategoryId(storeCategoryId);
    }
}
