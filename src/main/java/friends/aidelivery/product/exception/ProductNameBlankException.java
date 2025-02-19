package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ProductNameBlankException extends CustomBadRequestException {

    public ProductNameBlankException() {
        super("이름은 공백을 제외한 1자 이상이어야 합니다.");
    }
}
