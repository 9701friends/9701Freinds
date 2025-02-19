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
import friends.aidelivery.product.exception.ProductNotSellingException;
import friends.aidelivery.product.exception.ProductsNotFoundException;
import friends.aidelivery.store.domain.Store;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
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

    @Transactional
    public ProductResponse createProduct(final ProductCreateRequest request) {

        final UUID productCategoryId = request.productCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
            .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));

        Product product = new Product(productCategory, request.name(), request.content(),
            request.price(), ProductStatus.SELLING);

        Product saved = productRepository.save(product);
        return ProductResponse.of(saved);
    }

    /*
        주문 생성시 상품 유효성 검증
        1. 상품 존재 여부 체크
        2. 상품 상태가 SELLING 인지 체크
     */
    @Transactional
    public List<Product> findAllByProductIds(List<UUID> productIds) {
        return productIds.stream()
            .map(this::findProductById)
            .peek(this::checkSelling).toList();
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(ProductsNotFoundException::new);
    }

    private void checkSelling(final Product product) {
        if (product.getStatus() != ProductStatus.SELLING) {
            throw new ProductNotSellingException(product.getId());
        }
    }

}
