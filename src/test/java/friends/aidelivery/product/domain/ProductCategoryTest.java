package friends.aidelivery.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.store.domain.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ProductCategoryTest {

    @Mock // store mock 처리
    private Store store;

    @InjectMocks // productCategory에 store 주입
    private ProductCategory productCategory;

    @DisplayName("상품 카테고리 생성이 성공한다.")
    @Test
    void productCategory_success() {
        // given
        final String name = "카테고리 이름";
        final String content = "카테고리 내용";
        // when
        productCategory = new ProductCategory(store, name, content);
        // then
        assertNotNull(productCategory);
        assertEquals(name, productCategory.getName().getValue());
        assertEquals(content, productCategory.getContent().getValue());
    }
}