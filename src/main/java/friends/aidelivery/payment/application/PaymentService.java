package friends.aidelivery.payment.application;

import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.domain.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
//lombok - final 붙은 필드 생성자 자동생성 / autowired 없이 di주입가능
@Service
public class PaymentService {

    //@Autowired
    private final PaymentRepository paymentRepository;

    //결제 요청 처리 로직
    @Transactional
    public Payment requestPayment(String user_id, String order_id, Double payment, String createdBy) {
        Payment p = new Payment(user_id, order_id, payment, "REQUESTED", LocalDateTime.now(), createdBy );

        //새 객체를 만들 때,setter보다는 생성자로 만드는 것이 더 좋다!
        //Payment p = new Payment();
        // p.setUserId(user_id);
//        p.setOrderId(order_id);
//        p.setPayment(payment);
//        p.setPaymentState("REQUESTED");
//        p.setCreatedAt(LocalDateTime.now());
//        p.setCreatedBy(createdBy);

        // 결제 요청 저장
        return paymentRepository.save(p);
    }

    //결제 승인 처리 로직
    @Transactional
    public Payment approvePayment(UUID paymentIdx, String updatedBy) {
        Payment payment = paymentRepository.findById(paymentIdx)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        //결제 상태를 approved로 변경
        payment.setPaymentState("APPROVED");
        payment.setUpdatedAt(LocalDateTime.now()); //현재 시간
        payment.setUpdatedBy(updatedBy); //수정자 설정
        
        return paymentRepository.save(payment); //업데이트 정보 저장
    }

    //결제 취소 처리 로직
    public void cancelPayment(UUID payment_idx, String updated_by) {
        Optional<Payment> paymentOptional = paymentRepository.findById(payment_idx);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPaymentState("CANCELED");  // 상태 변경
            payment.setUpdatedBy(updated_by);
            payment.setUpdatedAt(LocalDateTime.now());
            payment.setDeletedAt(LocalDateTime.now());
            payment.setDeletedBy(updated_by);

            //상태 저장
            paymentRepository.save(payment);
        } else {
            throw new EntityNotFoundException("해당 결제를 찾을 수 없습니다.");
        }
    }
}
