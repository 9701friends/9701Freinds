package friends.aidelivery.product.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;

public class ProductsNotFoundException extends CustomNotFoundException {

    public ProductsNotFoundException() {
        super("일부 상품이 존재하지 않습니다.");
    }
}
