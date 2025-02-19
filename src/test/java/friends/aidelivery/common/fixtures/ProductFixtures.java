package friends.aidelivery.common.fixtures;

import friends.aidelivery.product.application.dto.request.ProductUpdateRequest;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.enums.ProductStatus;

public class ProductFixtures {

    // NAME
    public static final String FIRST_NAME = "상품 이름1";
    public static final String SECOND_NAME = "상품 이름2";
    public static final String THIRD_NAME = "상품 이름3";

    // CONTENT
    public static final String FIRST_CONTENT = "상품 내용1";
    public static final String SECOND_CONTENT = "상품 내용2";
    public static final String THIRD_CONTENT = "상품 내용3";

    // PRICE
    public static final Long FIRST_PRICE = 10000L;
    public static final Long SECOND_PRICE = 20000L;
    public static final Long THIRD_PRICE = 30000L;
    // STATUS
    public static final ProductStatus SELLING = ProductStatus.SELLING;
    public static final ProductStatus HOLD = ProductStatus.HOLD;
    public static final ProductStatus SOLD_OUT = ProductStatus.SOLD_OUT;

    // REQUEST
    public static ProductUpdateRequest PRODUCT_UPDATE_REQUEST(final String name,
        final String content, final Long price) {
        return new ProductUpdateRequest(name, content, price);
    }

    // ENTITY
    public static Product PRODUCT_1(final ProductCategory productCategory) {
        return new Product(productCategory, FIRST_NAME, FIRST_CONTENT, FIRST_PRICE, SELLING);
    }

    public static Product PRODUCT_2(final ProductCategory productCategory) {
        return new Product(productCategory, SECOND_NAME, SECOND_CONTENT, SECOND_PRICE, HOLD);
    }

    public static Product PRODUCT_3(final ProductCategory productCategory) {
        return new Product(productCategory, THIRD_NAME, THIRD_CONTENT, THIRD_PRICE, SOLD_OUT);
    }
}
