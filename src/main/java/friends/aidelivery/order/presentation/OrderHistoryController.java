package friends.aidelivery.order.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.order.application.OrderHistoryService;
import friends.aidelivery.order.application.dto.response.OrderHistoryListResponse;
import friends.aidelivery.order.application.dto.response.OrderHistoryResponse;
import friends.aidelivery.user.domain.enums.UserRoleEnum.Authority;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-histories")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse> getOrderHistoryForCustomer(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId) {
        OrderHistoryResponse response = orderHistoryService.getOrderHistoryForCustomer(userDetails,
            orderId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> getOrderHistoryListForCustomer(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String query
    ) {
        OrderHistoryListResponse response = orderHistoryService.searchOrderHistory(userDetails,
            page, size, sort, query);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({Authority.OWNER, Authority.MANAGER, Authority.MASTER})
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<CommonResponse> getOrderHistoryListForOwner(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID storeId,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String sort) {
        OrderHistoryListResponse response = orderHistoryService.getOrderHistoryListForOwner(
            userDetails, storeId,
            page, size, sort);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

}
