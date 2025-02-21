package friends.aidelivery.store.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.repository.StoreRegionMappingRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreRegionMappingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRegionMappingRepositoryImpl implements StoreRegionMappingRepository {

    private final StoreRegionMappingJpaRepository storeRegionMappingJpaRepository;
    private final JPAQueryFactory queryFactory;

}
