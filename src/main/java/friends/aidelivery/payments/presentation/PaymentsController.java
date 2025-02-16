package friends.aidelivery.payments.presentation;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.domain.bo.PaymentsService;
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

    private final PaymentsService paymentsService;

    private PaymentsRepository paymentsRepository;

    @Autowired
    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    // 결제 요청 (POST /payments)
    @PostMapping
    public ResponseEntity<Payments> createPayment(@RequestBody Payments payment,
                                                  @RequestHeader("Authorization") String authorization) {

        String token = authorization.replace("Bearer ", "");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        payment.setUserId(userId);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCreatedBy(userId);

        // 결제 저장
        Payments savedPayment = paymentsRepository.save(payment);

        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);  // 결제 요청이 성공적으로 처리되면 201 반환
    }


    @GetMapping("/{paymentIdx}")
    public ResponseEntity<Payments> getPayment(@PathVariable UUID paymentIdx) {
        Optional<Payments> payment = paymentsService.getPayment(paymentIdx);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{paymentIdx}/state")
    public ResponseEntity<Payments> updatePaymentState(@PathVariable UUID paymentIdx, @RequestBody String newState) {
        Optional<Payments> updatedPayment = paymentsService.updatePaymentState(paymentIdx, newState);
        return updatedPayment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{paymentIdx}")
    public ResponseEntity<Payments> deletePayment(@PathVariable UUID paymentIdx) {
        Optional<Payments> deletedPayment = paymentsService.deletePayment(paymentIdx);
        return deletedPayment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
