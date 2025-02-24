package friends.aidelivery.store.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.repository.RegionRepository;
import friends.aidelivery.store.infrastructure.jpa.RegionJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static friends.aidelivery.store.domain.QRegion.region;
import static friends.aidelivery.store.domain.QStoreRegionMapping.storeRegionMapping;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Region save(Region region) {
        return jpaRepository.save(region);
    }

    @Override
    public Optional<Region> findById(UUID regionId) {
        return jpaRepository.findById(regionId);
    }

    @Override
    public void softDeleteRegion(UUID regionId) {
        queryFactory
                .update(region)
                .set(region.isDeleted,true)
                .where(region.id.eq(regionId))
                .execute();

        queryFactory
                .update(storeRegionMapping)
                .set(storeRegionMapping.isDeleted,true)
                .where(storeRegionMapping.id.eq(regionId))
                .execute();
    }
}
