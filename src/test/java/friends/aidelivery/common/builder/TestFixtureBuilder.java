package friends.aidelivery.common.builder;

import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.store.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFixtureBuilder {

    @Autowired
    private BuilderSupporter supporter;

    public ProductCategory buildProductCategory(final ProductCategory productCategory) {
        return supporter.productCategoryRepository().save(productCategory);
    }

    public Product buildProduct(final Product product) {
        return supporter.productRepository().save(product);
    }

    public Store buildStore(final Store store) {
        return supporter.storeRepository().save(store);
    }
}
