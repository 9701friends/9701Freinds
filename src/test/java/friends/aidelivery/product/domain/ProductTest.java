package friends.aidelivery.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.domain.enums.ProductStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ProductTest {

    @Mock
    private ProductCategory productCategory;
    @InjectMocks
    private Product product;

    @DisplayName("상품 생성이 성공한다.")
    @Test
    void product_success() {
        // given
        final String name = "상품 이름";
        final String content = "상품 설명";
        final Long price = 10000L;
        final ProductStatus status = ProductStatus.SELLING;
        // when
        Product product = new Product(productCategory, name, content, price, status);
        // then
        assertNotNull(product);
        assertEquals(name, product.getName().getValue());
        assertEquals(content, product.getContent().getValue());
        assertEquals(price, product.getPrice().getValue());
        assertEquals(status, product.getStatus());
    }
}