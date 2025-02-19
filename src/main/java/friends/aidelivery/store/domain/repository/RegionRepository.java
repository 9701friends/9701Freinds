package friends.aidelivery.store.domain.repository;

import friends.aidelivery.store.domain.Region;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository {

    Region save(Region region);

    Optional<Region> findById(UUID regionId);
}
