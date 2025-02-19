package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;
import java.util.UUID;

public class ProductNotFoundException extends CustomNotFoundException {

    public ProductNotFoundException(final UUID id) {
        super(String.format(
            "조회한 상품이 존재하지 않습니다. - 요청 정보 { productId: %s}",
            id.toString()));
    }
}
