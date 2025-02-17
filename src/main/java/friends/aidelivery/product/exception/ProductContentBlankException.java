package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class ProductContentBlankException extends CustomBadRequestException {

    public ProductContentBlankException() {
        super("내용은 공백을 제외한 1자 이상이어야 합니다.");
    }
}
