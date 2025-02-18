package friends.aidelivery.payments.application;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.domain.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Transactional
    public Payments requestPayment(String userId, String orderId, Double paymentAmount, String createdBy) {
        // 결제 요청을 처리하는 로직
        Payments payment = new Payments();
        payment.setUserId(userId);
        payment.setOrderId(orderId);
        payment.setPayment(paymentAmount);
        payment.setPaymentState("REQUESTED");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCreatedBy(createdBy);

        // 결제 요청 저장
        return paymentsRepository.save(payment);
    }

    @Transactional
    public Payments approvePayment(UUID paymentIdx, String updatedBy) {
        // 결제 승인을 처리하는 로직
        Payments payment = paymentsRepository.findById(paymentIdx)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        //결제 상태를 approved로 변경
        payment.setPaymentState("APPROVED");
        payment.setUpdatedAt(LocalDateTime.now()); //현재 시간
        payment.setUpdatedBy(updatedBy); //수정자 설정
        
        return paymentsRepository.save(payment); //업데이트 정보 저장
    }
}
