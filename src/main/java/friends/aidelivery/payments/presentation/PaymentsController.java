package friends.aidelivery.payments.presentation;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.application.PaymentsService;
import friends.aidelivery.payments.application.dto.PaymentsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    //결제 요청
    @PostMapping
    public Payments requestPayment( @RequestBody PaymentsRequestDto paymentRequestDto) {
        Payments payment = paymentsService.requestPayment(
                paymentRequestDto.getUser_id(),
                paymentRequestDto.getOrder_id(),
                paymentRequestDto.getPayment(),
                paymentRequestDto.getCreated_by());

        //결제 요청이 들어오자마자 승인 수행
        paymentsService.approvePayment(payment.getPaymentIdx(), paymentRequestDto.getCreated_by());

        return payment;
    }

    //결제 승인
    @PostMapping("/approval")
    public ResponseEntity<String> approvePayment(@PathVariable UUID payment_idx,
                                  @RequestParam String updated_by) {
        
        // 결제 승인 로직
        paymentsService.approvePayment(payment_idx, updated_by);

        // 승인 변경 매세지
        return ResponseEntity.ok("승인이 변경되었습니다.");
    }
}
