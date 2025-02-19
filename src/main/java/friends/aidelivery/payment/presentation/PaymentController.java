package friends.aidelivery.payment.presentation;

import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.application.PaymentService;
import friends.aidelivery.payment.application.dto.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //결제 요청
    @PostMapping
    public Payment requestPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.requestPayment(
                paymentRequestDto.getUser_id(),
                paymentRequestDto.getOrder_id(),
                paymentRequestDto.getPayment(),
                paymentRequestDto.getCreated_by());

        //결제 요청이 들어오자마자 승인 수행
        paymentService.approvePayment(payment.getPaymentIdx(), paymentRequestDto.getCreated_by());

        return payment;
    }

    //결제 승인
    @PostMapping("/approval")
    public ResponseEntity<String> approvePayment(@PathVariable UUID payment_idx,
                                  @RequestParam String updated_by) {
        
        // 결제 승인 로직
        paymentService.approvePayment(payment_idx, updated_by);

        // 승인 변경 매세지
        return ResponseEntity.ok("승인되었습니다.");
    }

    //결제 취소
    @PostMapping("/cancel/{payment_idx}")
    public ResponseEntity<String> deletePayment(@PathVariable UUID payment_idx,
                                                @RequestParam(required = false) String updated_by) {
        // 결제 취소 로직
        paymentService.cancelPayment(payment_idx, updated_by);

        // 결제 승인 메시지 변경
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }
}
