package friends.aidelivery.payment.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.application.PaymentService;
import friends.aidelivery.payment.application.dto.PaymentRequestDto;
import friends.aidelivery.payment.exception.PaymentNotFoundException;
import friends.aidelivery.payment.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    //결제 요청
    @PostMapping
    public ResponseEntity<CommonResponse> requestPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.requestPayment(
                paymentRequestDto.getUser_id(),
                paymentRequestDto.getOrder_id(),
                paymentRequestDto.getPayment());

        //결제 요청이 들어오자마자 승인 수행
        paymentService.approvePayment(payment.getPaymentId());

        String response = "승인 됨" ;
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
                HttpStatus.OK);
    }

    //결제 승인
    @PostMapping("/approval/{paymentId}")
    public ResponseEntity<CommonResponse> approvePayment(@PathVariable UUID paymentId) {
        
        // 결제 승인 로직
        paymentService.approvePayment(paymentId);

        String response = "승인 됨" ;

        // 승인 변경 매세지
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
                HttpStatus.OK);
    }

    //결제 취소
    @PostMapping("/cancel/{paymentId}")
    public ResponseEntity<CommonResponse> deletePayment(@PathVariable UUID paymentId) {
        // 결제 취소 로직
        paymentService.cancelPayment(paymentId);

        String response = "취소 됨" ;

        // 결제 승인 메시지 변경
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
                HttpStatus.OK);
    }

    //결제 전체 조회
    //userID로 결제 조회
    @GetMapping("/get/user/{userId}")
    public List<Payment> getPaymentsByUserId(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    // 결제 아이디 하나만 들고오기
    // 하나만 들고오는 건 결제아이디로만
    // userId, 결제 아이디가 일치하는걸 들고오기
    @GetMapping("/get/{paymentId}")
    public Payment getPaymentByPaymentIdAndUserId(@PathVariable  UUID paymentId, @RequestBody PaymentRequestDto paymentRequestDto) {

        // 결제 아이디와 사용자 아이디가 일치하는 결제 정보를 조회
        Payment payment = paymentService.getPaymentByPaymentIdAndUserId(paymentId, paymentRequestDto.getUser_id());

        // 조회된 결제가 없다면 예외 처리
        if (payment == null) {
            throw new PaymentNotFoundException(paymentId);
        }
        return payment;
    }
}
