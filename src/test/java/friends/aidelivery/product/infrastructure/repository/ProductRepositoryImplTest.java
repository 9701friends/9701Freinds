package friends.aidelivery.product.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Autowired
    private ProductCategoryRepositoryImpl productCategoryRepository;


    @DisplayName("상품 저장이 성공한다.")
    @Test
    void save_access() {
        // given
        ProductCategory productCategory = new ProductCategory(null, "카테고리이름", "카테고리내용");
        productCategoryRepository.save(productCategory);
        final String name = "상품 이름";
        final String content = "상품 설명";
        final Long price = 10000L;
        final ProductStatus status = ProductStatus.SELLING;
        Product product = new Product(productCategory, name, content, price, status);

        // when
        Product saved = productRepository.save(product);

        // then
        assertNotNull(saved);
        assertEquals(saved, product);
    }
}