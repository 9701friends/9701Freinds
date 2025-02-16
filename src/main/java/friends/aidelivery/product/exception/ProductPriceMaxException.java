package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ProductPriceMaxException extends CustomBadRequestException {

    public ProductPriceMaxException(final long allowed, final long input) {
        super(String.format(
            "가격은 최대 가격보다 낮아야 합니다. - 요청 정보 { 최대 가격 : %d, 입력 값 : %d }",
            allowed,
            input));
    }
}
