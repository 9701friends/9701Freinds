package friends.aidelivery.store.infrastructure.jpa;

import friends.aidelivery.store.domain.StoreRegionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRegionMappingJpaRepository extends JpaRepository<StoreRegionMapping, UUID> {
}
