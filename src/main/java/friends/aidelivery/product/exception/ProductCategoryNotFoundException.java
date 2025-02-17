package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;
import java.util.UUID;

public class ProductCategoryNotFoundException extends CustomNotFoundException {

    public ProductCategoryNotFoundException(final UUID id) {
        super(String.format(
            "조회한 상품 카테고리가 존재하지 않습니다. - 요청 정보 { productCategoryId: %s}",
            id.toString()));
    }
}
