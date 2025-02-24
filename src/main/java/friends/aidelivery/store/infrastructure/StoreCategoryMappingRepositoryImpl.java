package friends.aidelivery.store.infrastructure;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import friends.aidelivery.store.domain.repository.StoreCategoryMappingRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreCategoryMappingJpaRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static friends.aidelivery.store.domain.QStore.store;
import static friends.aidelivery.store.domain.QStoreCategoryMapping.storeCategoryMapping;

@Repository
@RequiredArgsConstructor
public class StoreCategoryMappingRepositoryImpl implements StoreCategoryMappingRepository {

    private final StoreCategoryMappingJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;


    /**
     * 호출 전 StoreCategoryMapping의 isDelete 필드를 확인할 것
     * @param storeCategoryId
     * @param sortBy
     * @param isASC
     * @param pageable
     * @return
     */
    @Override
    public Page<Store> findStoresByCategoryId(UUID storeCategoryId, String sortBy, boolean isASC, Pageable pageable) {
        List<Store> storeList = queryFactory
                .select(store)
                .from(storeCategoryMapping)
                .join(storeCategoryMapping.store,store)
                .where(
                        storeCategoryMapping.storeCategory.id.eq(storeCategoryId),
                        store.isDeleted.eq(false)
                )
                .orderBy(getSortedColumn(sortBy,isASC))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(store.count())
                .from(storeCategoryMapping)
                .where(
                        storeCategoryMapping.storeCategory.id.eq(storeCategoryId),
                        store.isDeleted.eq(false)
                )
                .fetchOne();

        return new PageImpl<>(storeList,pageable,total != null ? total : 0);
    }

    private OrderSpecifier<?> getSortedColumn(String sortBy, boolean isAsc) {
        if ("rating".equalsIgnoreCase(sortBy)) {
            return isAsc ? store.averageRating.value.asc() : store.averageRating.value.desc();
        } else if ("name".equalsIgnoreCase(sortBy)) {
            return isAsc ? store.name.value.asc() : store.name.value.desc();
        } else if ("reviewCount".equalsIgnoreCase(sortBy)) {
            return isAsc ? store.averageRating.quantity.asc() : store.averageRating.quantity.desc();
        } else { // 아무 인자도 없으면 생성일 기준으로..
            return isAsc ? store.createdAt.asc() : store.createdAt.desc();
        }
    }
}
