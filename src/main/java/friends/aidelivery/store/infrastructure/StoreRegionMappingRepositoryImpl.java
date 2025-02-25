package friends.aidelivery.store.infrastructure;

import static friends.aidelivery.store.domain.QRegion.region;
import static friends.aidelivery.store.domain.QStore.store;
import static friends.aidelivery.store.domain.QStoreCategoryMapping.storeCategoryMapping;
import static friends.aidelivery.store.domain.QStoreRegionMapping.storeRegionMapping;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRegionMappingRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreRegionMappingJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRegionMappingRepositoryImpl implements StoreRegionMappingRepository {

    private final StoreRegionMappingJpaRepository storeRegionMappingJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Store> findStoresByRegionId(UUID regionUUID, String sortBy, boolean isAsc,
        Pageable pageable) {
        List<Store> storeList = queryFactory
            .select(store)
            .from(storeRegionMapping)
            .join(storeRegionMapping.store,store)
            .where(
                storeRegionMapping.region.id.eq(regionUUID),
                store.isDeleted.eq(false)
            )
            .orderBy(getSortedColumn(sortBy,isAsc))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(store.count())
            .from(storeRegionMapping)
            .where(
                storeRegionMapping.region.id.eq(regionUUID),
                store.isDeleted.eq(false)
            )
            .fetchOne();

        return new PageImpl<>(storeList,pageable,total != null ? total : 0);
    }

    private OrderSpecifier<?> getSortedColumn(String sortBy, boolean isAsc) {
        if ("name".equalsIgnoreCase(sortBy)) {
            return isAsc ? store.name.value.asc() : store.name.value.desc();
        } else { // 아무 인자도 없으면 생성일 기준으로..
            return isAsc ? store.createdAt.asc() : store.createdAt.desc();
        }
    }
}
