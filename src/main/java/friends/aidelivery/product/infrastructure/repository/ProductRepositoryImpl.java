package friends.aidelivery.product.infrastructure.repository;

import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.product.infrastructure.repository.jpa.ProductJpaRepository;
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
}
