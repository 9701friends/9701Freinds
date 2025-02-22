package friends.aidelivery.common.fixtures;

import friends.aidelivery.store.domain.Store;

public class StoreFixtures {

    // NAME
    public static final String FIRST_NAME = "store1";
    public static final String SECOND_NAME = "store2";
    public static final String THIRD_NAME = "store3";

    // ADDRESS
    public static final String FIRST_ADDRESS = "address1";
    public static final String SECOND_ADDRESS = "address2";
    public static final String THIRD_ADDRESS = "address3";

    // STORE_NUMBER
    public static final String FIRST_STORE_NUMBER = "010-1111-1111";
    public static final String SECOND_STORE_NUMBER = "010-2222-2222";
    public static final String THIRD_STORE_NUMBER = "010-3333-3333";

    // ENTITY
    public static Store STORE_1 = new Store(FIRST_NAME, FIRST_ADDRESS, FIRST_STORE_NUMBER, null);
//    public static Store STORE_2 = new Store(SECOND_NAME, SECOND_ADDRESS, SECOND_STORE_NUMBER);
//    public static Store STORE_3 = new Store(THIRD_NAME, THIRD_ADDRESS, THIRD_STORE_NUMBER);

}
