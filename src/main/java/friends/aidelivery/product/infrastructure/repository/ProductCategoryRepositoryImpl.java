package friends.aidelivery.product.infrastructure.repository;

import static friends.aidelivery.product.domain.QProductCategory.productCategory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.product.application.dto.request.ProductCategorySearchCond;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.infrastructure.repository.jpa.ProductCategoryJpaRepository;
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
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return jpaRepository.save(productCategory);
    }

    @Override
    public Optional<ProductCategory> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }

    @Override
    public Page<ProductCategory> searchCategory(ProductCategorySearchCond cond,
        Pageable pageable) {
        BooleanBuilder builder = getBooleanBuilder(cond);

        List<ProductCategory> productCategories = fetchProductCategories(builder,
            pageable);

        Long total = fetchTotalCount(builder);

        return new PageImpl<>(productCategories, pageable, total);
    }

    private BooleanBuilder getBooleanBuilder(ProductCategorySearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();

        if (cond.storeId() != null) {
            builder.and(productCategory.store.id.eq(cond.storeId()));
        }

        if (!cond.isDeleted()) {
            builder.and(productCategory.isDeleted.eq(false));
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
                orderSpecifiers.add(new OrderSpecifier<>(direction, productCategory.createdAt));
            }
            if ("updatedAt".equals(sortBy)) {
                orderSpecifiers.add(new OrderSpecifier<>(direction, productCategory.updatedAt));
            }
        });
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private List<ProductCategory> fetchProductCategories(BooleanBuilder builder,
        Pageable pageable) {
        return queryFactory.selectFrom(productCategory)
            .where(builder)
            .orderBy(getOrderSpecifiers(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private Long fetchTotalCount(BooleanBuilder builder) {
        Long total = queryFactory
            .select(productCategory.count())
            .from(productCategory)
            .where(builder)
            .fetchOne();
        return total == null ? 0L : total;
    }
}
