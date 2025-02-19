package friends.aidelivery.product.application;

import static friends.aidelivery.common.fixtures.StoreFixtures.STORE_1;
import static org.assertj.core.api.Assertions.assertThat;

import friends.aidelivery.common.ServiceTest;
import friends.aidelivery.common.fixtures.ProductCategoryFixtures;
import friends.aidelivery.common.fixtures.ProductFixtures;
import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCategoryUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductStatusUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductUpdateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.product.domain.repository.ProductRepository;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductServiceTest extends ServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("상품 카테고리 생성이 성공한다.")
    @Test
    void createProductCategory_success() {
        // given
        final UUID storeId = testFixtureBuilder.buildStore(STORE_1).getId();
        final ProductCategoryCreateRequest request = new ProductCategoryCreateRequest(
            storeId, ProductCategoryFixtures.FIRST_NAME, ProductCategoryFixtures.FIRST_CONTENT);
        // when
        ProductCategoryResponse response = productService.createProductCategory(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.storeId()).isEqualTo(storeId);
        assertThat(response.name()).isEqualTo(ProductCategoryFixtures.FIRST_NAME);
        assertThat(response.content()).isEqualTo(ProductCategoryFixtures.FIRST_CONTENT);
    }

    @DisplayName("상품 생성이 성공한다.")
    @Test
    void createProduct_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory productCategory1 = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        final UUID productCategory1Id = productCategory1.getId();
        final ProductCreateRequest request = new ProductCreateRequest(productCategory1Id,
            ProductFixtures.FIRST_NAME, ProductFixtures.FIRST_CONTENT, ProductFixtures.FIRST_PRICE);
        // when
        ProductResponse response = productService.createProduct(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.productCategoryId()).isEqualTo(productCategory1Id);
        assertThat(response.name()).isEqualTo(ProductFixtures.FIRST_NAME);
        assertThat(response.content()).isEqualTo(ProductFixtures.FIRST_CONTENT);
        assertThat(response.price()).isEqualTo(ProductFixtures.FIRST_PRICE);
        assertThat(response.status()).isEqualTo(ProductStatus.SELLING);
    }

    @DisplayName("상품의 카테고리 변경시 상품카테고리에도 반영된다.")
    @Test
    void updateCategoryOfProduct_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory oldCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        final ProductCategory newCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        final Product oldProduct = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_1(oldCategory));

        // when
        ProductResponse updated = productService.updateCategoryOfProduct(oldProduct.getId(),
            newCategory.getId());
        // then
        assertThat(updated).isNotNull();
        assertThat(updated.productCategoryId()).isEqualTo(newCategory.getId());
        assertThat(oldCategory.getProducts()).isEmpty();
        assertThat(newCategory.getProducts().get(0).getId()).isEqualTo(updated.productId());
    }

    @DisplayName("상품 정보 변경이 성공한다.")
    @Test
    void updateProduct_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory productCategory1 = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        final Product product = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_1(productCategory1));
        final UUID productId = product.getId();
        final ProductUpdateRequest request = new ProductUpdateRequest(ProductFixtures.SECOND_NAME,
            ProductFixtures.SECOND_CONTENT, ProductFixtures.SECOND_PRICE);

        // when
        final ProductResponse newProduct = productService.updateProduct(productId, request);

        // then
        assertThat(newProduct).isNotNull();
        assertThat(newProduct.productCategoryId()).isEqualTo(productCategory1.getId());
        assertThat(newProduct.productId()).isEqualTo(productId);
        assertThat(newProduct.status()).isEqualTo(ProductStatus.SELLING);
        assertThat(newProduct.price()).isEqualTo(ProductFixtures.SECOND_PRICE);
        assertThat(newProduct.name()).isEqualTo(ProductFixtures.SECOND_NAME);
        assertThat(newProduct.content()).isEqualTo(ProductFixtures.SECOND_CONTENT);
    }

    @DisplayName("상품카테고리 정보 변경이 성공한다.")
    @Test
    void updateProductCategory_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory oldProductCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        final ProductCategoryUpdateRequest request = new ProductCategoryUpdateRequest(store.getId(),
            ProductCategoryFixtures.SECOND_NAME, ProductCategoryFixtures.SECOND_CONTENT);
        // when
        final ProductCategoryResponse updated = productService.updateProductCategory(
            oldProductCategory.getId(), request);
        // then
        assertThat(updated).isNotNull();
        assertThat(updated.storeId()).isEqualTo(oldProductCategory.getStore().getId());
        assertThat(updated.productCategoryId()).isEqualTo(oldProductCategory.getId());
        assertThat(updated.name()).isEqualTo(ProductCategoryFixtures.SECOND_NAME);
        assertThat(updated.content()).isEqualTo(ProductCategoryFixtures.SECOND_CONTENT);
    }

    @DisplayName("여러 상품의 상태를 숨김으로 변경이 성공한다.")
    @Test
    void updateStatus_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory productCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        Product product1 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_1(productCategory));
        Product product2 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_2(productCategory));
        Product product3 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_3(productCategory));
        List<UUID> productIds = List.of(product1.getId(), product2.getId(), product3.getId());
        ProductStatusUpdateRequest request = new ProductStatusUpdateRequest(productIds,
            ProductStatus.HOLD);
        // when
        productService.updateStatus(request);
        Product updated1 = productService.findProductById(product1.getId());
        Product updated2 = productService.findProductById(product2.getId());
        Product updated3 = productService.findProductById(product3.getId());

        // then
        assertThat(updated1.getId()).isEqualTo(product1.getId());
        assertThat(updated2.getId()).isEqualTo(product2.getId());
        assertThat(updated3.getId()).isEqualTo(product3.getId());
        assertThat(updated1.getStatus()).isEqualTo(ProductStatus.HOLD);
        assertThat(updated2.getStatus()).isEqualTo(ProductStatus.HOLD);
        assertThat(updated3.getStatus()).isEqualTo(ProductStatus.HOLD);
    }

    @DisplayName("여러 상품의 소프트 삭제시 상품카테고리의 상품리스트에서 삭제된다..")
    @Test
    void softDelete_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory productCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        Product product1 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_1(productCategory));
        Product product2 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_2(productCategory));
        List<UUID> productIds = List.of(product1.getId(), product2.getId());
        // when
        productService.softDeleteProducts(productIds);
        Product deleted1 = productService.findProductById(product1.getId());
        Product deleted2 = productService.findProductById(product2.getId());

        // then
        assertThat(deleted1.getId()).isEqualTo(product1.getId());
        assertThat(deleted2.getId()).isEqualTo(product2.getId());
        assertThat(deleted1.getStatus()).isEqualTo(ProductStatus.HOLD);
        assertThat(deleted2.getStatus()).isEqualTo(ProductStatus.HOLD);
        assertThat(deleted1.getDeletedAt()).isNotNull();
        assertThat(deleted2.getDeletedAt()).isNotNull();
        assertThat(productCategory.getProducts()).isEmpty();
    }

    @DisplayName("상품Id로 상품을 조회시 성공한다.")
    @Test
    void getProductById_success() {
        // given
        final Store store = testFixtureBuilder.buildStore(STORE_1);
        final ProductCategory productCategory = testFixtureBuilder.buildProductCategory(
            ProductCategoryFixtures.PRODUCT_CATEGORY_1(store));
        Product product1 = testFixtureBuilder.buildProduct(
            ProductFixtures.PRODUCT_1(productCategory));
        // when
        ProductResponse response = productService.getProductById(product1.getId());
        // then
        assertThat(response).isNotNull();
        assertThat(response.productId()).isEqualTo(product1.getId());
    }
}