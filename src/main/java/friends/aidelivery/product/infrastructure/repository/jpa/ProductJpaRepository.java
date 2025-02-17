package friends.aidelivery.product.infrastructure.repository.jpa;

import friends.aidelivery.product.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

}
