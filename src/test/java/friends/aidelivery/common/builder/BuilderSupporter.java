package friends.aidelivery.common.builder;

import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.store.domain.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    public ProductRepository productRepository() {
        return productRepository;
    }

    public ProductCategoryRepository productCategoryRepository() {
        return productCategoryRepository;
    }

    public StoreRepository storeRepository() {
        return storeRepository;
    }
}
