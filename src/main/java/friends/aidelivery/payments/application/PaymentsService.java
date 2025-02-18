package friends.aidelivery.payments.application;

import friends.aidelivery.payments.domain.Payments;
import friends.aidelivery.payments.domain.repository.PaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    //결제 요청 처리 로직
    @Transactional
    public Payments requestPayment(String user_id, String order_id, Double payment, String createdBy) {
        Payments p = new Payments();
        p.setUserId(user_id);
        p.setOrderId(order_id);
        p.setPayment(payment);
        p.setPaymentState("REQUESTED");
        p.setCreatedAt(LocalDateTime.now());
        p.setCreatedBy(createdBy);

        // 결제 요청 저장
        return paymentsRepository.save(p);
    }

    //결제 승인 처리 로직
    @Transactional
    public Payments approvePayment(UUID paymentIdx, String updatedBy) {
        Payments payment = paymentsRepository.findById(paymentIdx)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        //결제 상태를 approved로 변경
        payment.setPaymentState("APPROVED");
        payment.setUpdatedAt(LocalDateTime.now()); //현재 시간
        payment.setUpdatedBy(updatedBy); //수정자 설정
        
        return paymentsRepository.save(payment); //업데이트 정보 저장
    }

    //결제 삭제 처리 로직
    public void cancelPayment(UUID payment_idx, String updated_by) {
        Optional<Payments> paymentOptional = paymentsRepository.findById(payment_idx);


        if (paymentOptional.isPresent()) {
            Payments payment = paymentOptional.get();
            payment.setPaymentState("CANCELED");  // 상태 변경
            payment.setUpdatedBy(updated_by);
            payment.setUpdatedAt(LocalDateTime.now());

            paymentsRepository.save(payment);
        } else {
            throw new EntityNotFoundException("해당 결제를 찾을 수 없습니다.");
        }
    }
}
