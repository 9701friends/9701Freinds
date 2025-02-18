package friends.aidelivery.store.infrastructure;

import static friends.aidelivery.store.domain.QStore.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.infrastructure.jpa.StoreJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final StoreJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Store save(Store store) {
        return jpaRepository.save(store);
    }

    @Override
    public Optional<Store> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }

    @Override
    public Page<Store> findByName(String keyword, Pageable pageable) {
        List<Store> stores = queryFactory
            .selectFrom(store)
            .where(store.name.value.containsIgnoreCase(keyword)) // 대소문자 무시
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(store.count())
            .from(store)
            .where(store.name.value.containsIgnoreCase(keyword))
            .fetchOne();

        return new PageImpl<>(stores, pageable, total != null ? total : 0);
    }
}
