package friends.aidelivery.store.infrastructure;

import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.repository.RegionRepository;
import friends.aidelivery.store.infrastructure.jpa.RegionJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository jpaRepository;

    @Override
    public Region save(Region region) {
        return jpaRepository.save(region);
    }

    @Override
    public Optional<Region> findById(UUID regionId) {
        return jpaRepository.findById(regionId);
    }
}
