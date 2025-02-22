package friends.aidelivery.order.infrastructure.repository;

import static friends.aidelivery.order.domain.QOrderHistory.orderHistory;
import static friends.aidelivery.order.domain.QOrderProductHistory.orderProductHistory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.order.domain.OrderHistory;
import friends.aidelivery.order.domain.repository.OrderHistoryRepository;
import friends.aidelivery.order.infrastructure.jpa.OrderHistoryJpaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final OrderHistoryJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        return jpaRepository.save(orderHistory);
    }

    @Override
    public Optional<OrderHistory> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId);
    }

    @Override
    public Page<OrderHistory> search(Long userId, Pageable pageable, String query) {
        BooleanBuilder booleanBuilder = getBooleanBuilder(userId, query);
        List<OrderHistory> orderHistories = fetchOrderHistories(booleanBuilder, pageable, query);
        Long totalCount = fetchTotalCount(booleanBuilder);
        return new PageImpl<>(orderHistories, pageable, totalCount);
    }

    @Override
    public Page<OrderHistory> findAllByStoreId(UUID storeId, Pageable pageable) {
        return jpaRepository.findAllByStoreId(storeId, pageable);
    }

    private List<OrderHistory> fetchOrderHistories(BooleanBuilder builder,
        Pageable pageable, String query) {
        return queryFactory.select(orderHistory)
            .from(orderHistory)
            .leftJoin(orderProductHistory)
            .on(orderProductHistory.orderHistory.id.eq(orderHistory.id))
            .where(builder)
            .orderBy(getOrderSpecifiers(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanBuilder getBooleanBuilder(final Long userId, final String query) {
        BooleanBuilder builder = new BooleanBuilder();

        if (userId != null) {
            builder.and(orderHistory.userId.eq(userId));

            if (query != null) {
                BooleanBuilder queryCondition = new BooleanBuilder();
                queryCondition.or(orderHistory.storeName.containsIgnoreCase(query));
                queryCondition.or(orderProductHistory.productName.containsIgnoreCase(query));

                builder.and(queryCondition);
            }
        }

        return builder;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        Sort sort = pageable.getSort();

        sort.forEach(order -> {
            String sortBy = order.getProperty();
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            if ("createdAt".equals(sortBy)) {
                orderSpecifiers.add(new OrderSpecifier<>(direction, orderHistory.createdAt));
            }
            if ("updatedAt".equals(sortBy)) {
                orderSpecifiers.add(new OrderSpecifier<>(direction, orderHistory.updatedAt));
            }
        });
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private Long fetchTotalCount(BooleanBuilder builder) {
        Long total = queryFactory
            .select(orderHistory.count())
            .from(orderHistory)
            .leftJoin(orderProductHistory)
            .on(orderProductHistory.orderHistory.id.eq(orderHistory.id))
            .where(builder)
            .fetchOne();
        return total == null ? 0L : total;
    }
}
