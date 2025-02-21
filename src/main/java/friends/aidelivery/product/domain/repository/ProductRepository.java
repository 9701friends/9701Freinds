package friends.aidelivery.product.domain.repository;

import friends.aidelivery.product.application.dto.request.ProductSearchCond;
import friends.aidelivery.product.domain.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID productId);

    Page<Product> search(ProductSearchCond cond, Pageable pageable);
}
