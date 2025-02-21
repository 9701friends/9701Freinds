package friends.aidelivery.store.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.repository.StoreCategoryRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreCategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import static friends.aidelivery.store.domain.QStoreCategory.storeCategory;
import static friends.aidelivery.store.domain.QStoreCategoryMapping.storeCategoryMapping;

@Repository
@RequiredArgsConstructor
public class StoreCategoryRepositoryImpl implements StoreCategoryRepository {

    private final StoreCategoryJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public StoreCategory save(StoreCategory storeCategory) {
        return jpaRepository.save(storeCategory);
    }

    @Override
    public Optional<StoreCategory> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }

    @Override
    public void softDeleteCategory(UUID uuid) {
        queryFactory
                .update(storeCategory)
                .set(storeCategory.isDeleted,true)
                .where(storeCategory.id.eq(uuid))
                .execute();

        queryFactory
                .update(storeCategoryMapping)
                .set(storeCategoryMapping.isDeleted,true)
                .where(storeCategoryMapping.id.eq(uuid))
                .execute();
    }
}
