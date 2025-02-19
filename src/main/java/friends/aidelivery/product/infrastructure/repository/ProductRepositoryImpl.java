package friends.aidelivery.product.infrastructure.repository;

import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.product.infrastructure.repository.jpa.ProductJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return jpaRepository.findById(productId);
    }
}
