package friends.aidelivery.product.infrastructure.repository;

import static friends.aidelivery.product.domain.QProduct.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.product.application.dto.request.ProductSearchCond;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.product.infrastructure.repository.jpa.ProductJpaRepository;
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
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return jpaRepository.findById(productId);
    }

    @Override
    public Page<Product> search(ProductSearchCond cond, Pageable pageable) {
        BooleanBuilder builder = getBooleanBuilder(cond);

        List<Product> products = fetchProducts(cond, builder, pageable);

        Long total = fetchTotalCount(builder);

        return new PageImpl<>(products, pageable, total);
    }

    private BooleanBuilder getBooleanBuilder(ProductSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();

        if (cond.storeId() != null) {
            builder.and(product.productCategory.store.id.eq(cond.storeId()));
        }
        if (cond.productCategoryId() != null) {
            builder.and(product.productCategory.id.eq(cond.productCategoryId()));
        }
        if (cond.status() != null) {
            builder.and(product.status.eq(cond.status()));
        }
        if (!cond.isDeleted()) {
            builder.and(product.isDeleted.eq(false));
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
                orderSpecifiers.add(new OrderSpecifier<>(direction, product.createdAt));
            }
            if ("updatedAt".equals(sortBy)) {
                orderSpecifiers.add(new OrderSpecifier<>(direction, product.updatedAt));
            }
        });
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private List<Product> fetchProducts(ProductSearchCond cond, BooleanBuilder builder,
        Pageable pageable) {
        return queryFactory.selectFrom(product)
            .where(builder)
            .orderBy(getOrderSpecifiers(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private Long fetchTotalCount(BooleanBuilder builder) {
        Long total = queryFactory
            .select(product.count())
            .from(product)
            .where(builder)
            .fetchOne();
        return total == null ? 0L : total;
    }
}
