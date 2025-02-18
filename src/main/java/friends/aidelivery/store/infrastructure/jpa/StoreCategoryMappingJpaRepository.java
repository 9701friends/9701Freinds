package friends.aidelivery.store.infrastructure.jpa;

import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryMappingJpaRepository extends
    JpaRepository<StoreCategoryMapping, UUID> {


    List<StoreCategoryMapping> findByStoreCategoryId(UUID uuid);
}
