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

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping
    public Payments requestPayment(@RequestParam String userId,
                                  @RequestParam String orderId,
                                  @RequestParam Double paymentAmount,
                                  @RequestParam String createdBy) {
        return paymentsService.requestPayment(userId, orderId, paymentAmount, createdBy);
    }

    @PostMapping("/approval")
    public Payments approvePayment(@PathVariable UUID paymentIdx,
                                  @RequestParam String updatedBy) {
        return paymentsService.approvePayment(paymentIdx, updatedBy);
    }
}
