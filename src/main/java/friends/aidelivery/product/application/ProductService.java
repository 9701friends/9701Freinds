package friends.aidelivery.product.application;

import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.product.exception.ProductCategoryNotFoundException;
import friends.aidelivery.store.application.StoreService;
import friends.aidelivery.store.domain.Store;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final StoreService storeService;

    public ProductCategoryResponse createProductCategory(ProductCategoryCreateRequest request) {
        /*
        todo 가게 유효성 검증 : request.storeId로 Store 찾기
         */
        Store store = new Store();

        ProductCategory productCategory = new ProductCategory(store, request.name(),
            request.content());

        ProductCategory saved = productCategoryRepository.save(productCategory);
        return ProductCategoryResponse.of(saved);
    }

    public ProductResponse createProduct(final ProductCreateRequest request) {

        final UUID productCategoryId = request.productCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
            .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        Product product = new Product(productCategory, request.name(), request.content(),
            request.price(), ProductStatus.SELLING);

        Product saved = productRepository.save(product);
        return ProductResponse.of(saved);
    }


}
