package friends.aidelivery.payments.presentation;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.domain.bo.PaymentsService;
import friends.aidelivery.payments.domain.dto.PaymentsRequestDto;
import friends.aidelivery.payments.domain.repository.PaymentsRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
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
                paymentRequestDto.getUserId(),
                paymentRequestDto.getOrderId(),
                paymentRequestDto.getPaymentAmount(),
                paymentRequestDto.getCreatedBy());

        //결제 요청이 들어오자마자 승인 수행
        paymentsService.approvePayment(payment.getPaymentIdx(), paymentRequestDto.getCreatedBy());

        return payment;
    }

    //결제 승인
    @PostMapping("/approval")
    public ResponseEntity<String> approvePayment(@PathVariable UUID paymentIdx,
                                  @RequestParam String updatedBy) {
        
        // 결제 승인 로직
        paymentsService.approvePayment(paymentIdx, updatedBy);

        // 승인 변경 매세지
        return ResponseEntity.ok("승인이 변경되었습니다.");
    }
}
