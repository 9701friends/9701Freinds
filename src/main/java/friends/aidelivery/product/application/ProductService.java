package friends.aidelivery.product.application;

import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCategorySearchCond;
import friends.aidelivery.product.application.dto.request.ProductCategoryUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductSearchCond;
import friends.aidelivery.product.application.dto.request.ProductStatusUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductUpdateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductCategorySearchResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import friends.aidelivery.product.application.dto.response.ProductSearchResponse;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.product.exception.ProductCategoryNotFoundException;
import friends.aidelivery.product.exception.ProductNotFoundException;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.exception.StoreNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ProductCategoryResponse createProductCategory(
        final ProductCategoryCreateRequest request) {

        // todo 유저 권한 검증
        final UUID storeId = request.storeId();
        final Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(storeId));

        ProductCategory productCategory = new ProductCategory(store, request.name(),
            request.content());

        ProductCategory saved = productCategoryRepository.save(productCategory);
        return ProductCategoryResponse.of(saved);
    }

    @Transactional
    public ProductResponse createProduct(final ProductCreateRequest request) {

        final UUID productCategoryId = request.productCategoryId();
        ProductCategory productCategory = findCategoryById(productCategoryId);

        Product product = new Product(productCategory, request.name(), request.content(),
            request.price(), ProductStatus.SELLING);

        Product saved = productRepository.save(product);
        productCategory.addProduct(product);

        return ProductResponse.of(saved);
    }

    @Transactional
    public ProductResponse updateProduct(final UUID productId, final ProductUpdateRequest request) {
        final Product product = findProductById(productId);
        product.update(request);
        return ProductResponse.of(product);
    }

    @Transactional
    public ProductCategoryResponse updateProductCategory(final UUID productCategoryId,
        final ProductCategoryUpdateRequest request) {
        final ProductCategory productCategory = findCategoryById(productCategoryId);
        productCategory.update(request);
        return ProductCategoryResponse.of(productCategory);
    }

    @Transactional
    public ProductResponse updateCategoryOfProduct(final UUID productId,
        final UUID productCategoryId) {
        final Product product = findProductById(productId);
        final ProductCategory newProductCategory = findCategoryById(productCategoryId);
        product.updateCategory(newProductCategory);
        return ProductResponse.of(product);
    }

    @Transactional
    public void updateStatus(ProductStatusUpdateRequest request) {
        final List<UUID> productIds = request.productIds();
        final ProductStatus status = request.status();
        productIds.stream().map(this::findProductById)
            .forEach(product -> product.updateStatus(status));
    }

    @Transactional
    public void softDeleteProducts(final List<UUID> productIds) {
        productIds.stream().map(this::findProductById).forEach(Product::softDelete);
    }

    @Transactional
    public void softDeleteProductCategories(List<UUID> productCategoryIds) {
        productCategoryIds.stream().map(this::findCategoryById)
            .forEach(ProductCategory::softDelete);
    }

    public ProductResponse getProductById(final UUID productId) {
        return ProductResponse.of(findProductById(productId));
    }

    public List<Product> findAllByProductIds(List<UUID> productIds) {
        return productIds.stream()
            .map(this::findProductById)
            .peek(Product::checkSelling).toList();
    }

    public Product findProductById(final UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public ProductCategory findCategoryById(final UUID productCategoryId) {
        return productCategoryRepository.findById(productCategoryId)
            .orElseThrow(() -> new ProductCategoryNotFoundException(productCategoryId));
    }

    public ProductSearchResponse search(final ProductSearchCond cond, final int page,
        final int size, final String sort) {
        Pageable pageable = getPageable(page, size, sort);
        return ProductSearchResponse.of(productRepository.search(cond, pageable));
    }

    public ProductCategorySearchResponse searchCategory(final ProductCategorySearchCond cond,
        final int page, final int size, final String sort) {
        Pageable pageable = getPageable(page, size, sort);
        return ProductCategorySearchResponse.of(
            productCategoryRepository.searchCategory(cond, pageable));
    }

    private Pageable getPageable(final int page, final int size, final String sort) {

        String[] sortParams = sort.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String param : sortParams) {
            String[] fieldAndDirection = param.trim().split("-");
            if (fieldAndDirection.length != 2) {
                throw new IllegalArgumentException(
                    "Invalid sort parameter format. Expected 'field direction'.");
            }

            String field = fieldAndDirection[0];
            String direction = fieldAndDirection[1].toUpperCase();
            Direction dir = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
            orders.add(new Sort.Order(dir, field));
        }

        Sort sortObj = Sort.by(orders);

        return PageRequest.of(page, size, sortObj);
    }
}
