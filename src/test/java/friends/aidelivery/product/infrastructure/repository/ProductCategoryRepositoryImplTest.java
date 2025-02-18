package friends.aidelivery.product.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.domain.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductCategoryRepositoryImplTest {

    @Autowired
    private ProductCategoryRepositoryImpl productCategoryRepository;

    @DisplayName("상품 카테고리 저장이 성공한다.")
    @Test
    void save_success() {
        // given
        ProductCategory productCategory = new ProductCategory(null, "카테고리이름", "카테고리내용");
        // when
        ProductCategory saved = productCategoryRepository.save(productCategory);
        // then
        assertNotNull(saved);
        assertEquals(productCategory, saved);
    }
}