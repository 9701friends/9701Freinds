package friends.aidelivery.order.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.order.application.OrderService;
import friends.aidelivery.order.application.dto.request.OrderCancelRequest;
import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.application.dto.response.OrderResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{orderId}")
    public ResponseEntity<CommonResponse> getOrderInfo(
        @PathVariable UUID orderId) {
        OrderResponse response = orderService.getOrderById(orderId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> createOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody OrderCreateRequest request) {
        OrderResponse response = orderService.createOrder(request,
            userDetails.getEmail());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PostMapping("{orderId}/cancel")
    public ResponseEntity<CommonResponse> cancelOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId,
        @RequestBody OrderCancelRequest request) {
        orderService.cancelOrder(orderId, userDetails.getEmail(), request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("{orderId}/accept")
    public ResponseEntity<CommonResponse> acceptOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId) {
        orderService.acceptOrder(orderId, userDetails.getEmail());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @PostMapping("{orderId}/reject")
    public ResponseEntity<CommonResponse> rejectOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID orderId) {
        orderService.rejectOrder(orderId, userDetails.getEmail());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}
