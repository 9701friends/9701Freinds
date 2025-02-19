package friends.aidelivery.product.domain.repository;

import friends.aidelivery.product.domain.Product;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID productId);
}
