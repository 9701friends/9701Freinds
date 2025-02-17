package friends.aidelivery.product.infrastructure.repository.jpa;

import friends.aidelivery.product.domain.ProductCategory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategory, UUID> {

}
