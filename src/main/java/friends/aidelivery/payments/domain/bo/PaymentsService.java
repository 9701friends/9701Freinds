package friends.aidelivery.payments.domain.bo;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.domain.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;

    @Autowired
    public PaymentsService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    // 결제 저장
    public Payments createPayment(Payments payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCreatedBy("System");
        return paymentsRepository.save(payment);
    }

    // 결제 조회
    public Optional<Payments> getPayment(UUID paymentIdx) {
        return paymentsRepository.findByPaymentIdx(paymentIdx);
    }

    // 결제 상태 업데이트
    public Optional<Payments> updatePaymentState(UUID paymentIdx, String newState) {
        Optional<Payments> payment = paymentsRepository.findByPaymentIdx(paymentIdx);
        payment.ifPresent(p -> {
            p.setPaymentState(newState);
            p.setUpdatedAt(LocalDateTime.now());
            p.setUpdatedBy("System");
            paymentsRepository.save(p);
        });
        return payment;
    }

    // 결제 삭제 (soft delete)
    public Optional<Payments> deletePayment(UUID paymentIdx) {
        Optional<Payments> payment = paymentsRepository.findByPaymentIdx(paymentIdx);
        payment.ifPresent(p -> {
            p.setDeletedAt(LocalDateTime.now());
            p.setDeletedBy("System");  // 예시로 시스템이 삭제한 것으로 설정
            paymentsRepository.save(p);
        });
        return payment;
    }
}
