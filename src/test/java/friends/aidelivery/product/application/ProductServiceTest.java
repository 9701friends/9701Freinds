package friends.aidelivery.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @DisplayName("상품 카테고리 생성이 성공한다.")
    @Test
    void createProductCategory_success() {
        // given
        final UUID storeId = UUID.randomUUID();
        final String name = "카테고리 이름";
        final String content = "카테고리 내용";
        final ProductCategoryCreateRequest request = new ProductCategoryCreateRequest(
            storeId, name, content);
        // when
        ProductCategoryResponse response = productService.createProductCategory(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(name);
        assertThat(response.content()).isEqualTo(content);
    }

    @DisplayName("상품 생성이 성공한다.")
    @Test
    void createProduct_success() {
        // given
        final ProductCategory productCategory = new ProductCategory(null, "카테고리이름", "카테고리내용");
        final UUID productCategoryId = productCategoryRepository.save(productCategory).getId();
        final String name = "상품 이름";
        final String content = "상품 내용";
        final Long price = 10000L;
        final ProductCreateRequest request = new ProductCreateRequest(
            productCategoryId, name, content, price);
        // when
        ProductResponse response = productService.createProduct(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.productCategoryId()).isEqualTo(productCategoryId);
        assertThat(response.name()).isEqualTo(name);
        assertThat(response.content()).isEqualTo(content);
        assertThat(response.price()).isEqualTo(price);
        assertThat(response.status()).isEqualTo(ProductStatus.SELLING);
    }
}