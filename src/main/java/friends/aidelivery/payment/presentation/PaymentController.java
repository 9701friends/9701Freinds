package friends.aidelivery.payment.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.payment.application.PaymentService;
import friends.aidelivery.payment.application.dto.PaymentRequest;
import friends.aidelivery.payment.application.dto.PaymentResponse;
import friends.aidelivery.user.domain.enums.UserRoleEnum.Authority;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<CommonResponse> getPaymentInfo(
        @PathVariable UUID paymentId) {
        PaymentResponse response = paymentService.getPayment(paymentId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse> getPaymentsByUserId(
        @PathVariable Long userId,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {
        List<PaymentResponse> response = paymentService.getPaymentsByUserId(userId, page, size,
            sortBy, isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({Authority.OWNER, Authority.MANAGER, Authority.MASTER})
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<CommonResponse> getPaymentsByStoreId(
        @PathVariable UUID storeId,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {
        List<PaymentResponse> response = paymentService.getPaymentsByStoreId(storeId, page, size,
            sortBy, isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> createPayment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request, userDetails);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<CommonResponse> cancelPayment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable UUID paymentId) {
        PaymentResponse response = paymentService.cancelPayment(paymentId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }
}
