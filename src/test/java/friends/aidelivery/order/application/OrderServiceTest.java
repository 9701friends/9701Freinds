package friends.aidelivery.order.application;

import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.application.dto.request.OrderProductRequest;
import friends.aidelivery.order.application.dto.response.OrderResponse;
import friends.aidelivery.order.domain.enums.OrderType;
import friends.aidelivery.product.application.ProductService;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.domain.ProductCategory;
import friends.aidelivery.product.domain.repository.ProductCategoryRepository;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.user.domain.vo.Email;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("주문 생성이 성공한다.")
    @Test
    void order_create_success() {
        // given
        final String email = "text1234@gmail.com";

        final String storeName = "테스트 가게 이름";
        final String storeAddress = "테스트 가게 주소";
        final String storeNumber = "010-1234-5668";
        final Store store = new Store(storeName, storeAddress, storeNumber);
        final UUID storeId = storeRepository.save(store).getId();

        final OrderType orderType = OrderType.DELIVERY;
        final String orderAddress = "테스트 주문 주소";
        final String orderComment = "테스트 요청사항";
        final LocalDateTime orderTime = LocalDateTime.now();

        final ProductCategory productCategory = new ProductCategory(store, "카테고리이름", "카테고리내용");
        final UUID productCategoryId = productCategoryRepository.save(productCategory).getId();
        final String productName1 = "테스트 상품 이름 1";
        final String productName2 = "테스트 상품 이름 2";
        final String content1 = "테스트 상품 내용";
        final String content2 = "테스트 상품 내용";
        final Long price1 = 10000L;
        final Long price2 = 20000L;
        final ProductCreateRequest request1 = new ProductCreateRequest(
            productCategoryId, productName1, content1, price1);
        final ProductCreateRequest request2 = new ProductCreateRequest(
            productCategoryId, productName2, content2, price2);
        final UUID productId1 = productService.createProduct(request1).productId();
        final UUID productId2 = productService.createProduct(request2).productId();

        final OrderProductRequest orderProductRequest1 = new OrderProductRequest(productId1, 2);
        final OrderProductRequest orderProductRequest2 = new OrderProductRequest(productId2, 4);

        final List<OrderProductRequest> orderProductRequests = List.of(orderProductRequest1,
            orderProductRequest2);

        final OrderCreateRequest request = new OrderCreateRequest(storeId, orderType, orderAddress,
            orderComment, orderTime, orderProductRequests);
        // when
        final OrderResponse orderResponse = orderService.createOrder(request, new Email(email));
        // then
        Assertions.assertThat(orderResponse).isNotNull();

    }
}