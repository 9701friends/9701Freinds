package friends.aidelivery.product.domain.repository;

import friends.aidelivery.product.domain.ProductCategory;
import java.util.Optional;
import java.util.UUID;

public interface ProductCategoryRepository {

    ProductCategory save(ProductCategory productCategory);

    Optional<ProductCategory> findById(UUID uuid);
}
