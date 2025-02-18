package friends.aidelivery.product.infrastructure.repository;

import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.infrastructure.repository.jpa.ProductCategoryJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository jpaRepository;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return jpaRepository.save(productCategory);
    }

    @Override
    public Optional<ProductCategory> findById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }
}
