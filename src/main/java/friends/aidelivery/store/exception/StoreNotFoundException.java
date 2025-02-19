package friends.aidelivery.store.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;
import java.util.UUID;

public class StoreNotFoundException extends CustomNotFoundException {

    public StoreNotFoundException(final UUID id) {
        super(String.format(
            "해당 가게가 존재하지 않습니다. - 요청 정보 { storeId: %s}",
            id.toString()));
    }
}
