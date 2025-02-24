package friends.aidelivery.product.domain.repository;

import friends.aidelivery.product.application.dto.request.ProductCategorySearchCond;
import friends.aidelivery.product.domain.ProductCategory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryRepository {

    ProductCategory save(ProductCategory productCategory);

    Optional<ProductCategory> findById(UUID uuid);

    Page<ProductCategory> searchCategory(ProductCategorySearchCond cond, Pageable pageable);
}
