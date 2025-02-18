package friends.aidelivery.store.infrastructure.jpa;

import friends.aidelivery.store.domain.Region;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<Region, UUID> {

}
