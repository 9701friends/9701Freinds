package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;
import java.util.UUID;

public class ProductNotSellingException extends CustomBadRequestException {

    public ProductNotSellingException(final UUID id) {
        super(String.format(
            "해당 상품은 현재 판매중이 아닙니다. - 요청 정보 { productId: %s}",
            id.toString()));
    }
}
