package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ProductPriceMinException extends CustomBadRequestException {

    public ProductPriceMinException(final long allowed, final long input) {
        super(String.format(
            "가격은 최저 가격보다 높아야 합니다. - 요청 정보 { 최저 가격 : %d, 입력 값 : %d }",
            allowed,
            input));
    }
}
