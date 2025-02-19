package friends.aidelivery.common.fixtures;

import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.store.domain.Store;

public class ProductCategoryFixtures {

    // NAME
    public static final String FIRST_NAME = "상품카테고리 이름1";
    public static final String SECOND_NAME = "상품카테고리 이름2";
    public static final String THIRD_NAME = "상품카테고리 이름3";

    // CONTENT
    public static final String FIRST_CONTENT = "상품카테고리 내용1";
    public static final String SECOND_CONTENT = "상품카테고리 내용2";
    public static final String THIRD_CONTENT = "상품카테고리 내용3";

    // ENTITY
    public static ProductCategory PRODUCT_CATEGORY_1(final Store store) {
        return new ProductCategory(store, FIRST_NAME, FIRST_CONTENT);
    }

    public static ProductCategory PRODUCT_CATEGORY_2(final Store store) {
        return new ProductCategory(store, SECOND_NAME, SECOND_CONTENT);
    }

    public static ProductCategory PRODUCT_CATEGORY_3(final Store store) {
        return new ProductCategory(store, THIRD_NAME, THIRD_CONTENT);
    }
}
