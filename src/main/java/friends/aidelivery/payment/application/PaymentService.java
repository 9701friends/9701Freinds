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
    //user_id는 String 으로, order_id는 유효아이디로
    //dto 활용해서 하기
    public Payment requestPayment(long user_id, UUID order_id, Double payment, String createdBy) {
        Payment p = new Payment(user_id, order_id, payment, "REQUESTED", LocalDateTime.now(), createdBy );

        // 결제 요청 저장
        return paymentRepository.save(p);
    }

    //결제 승인 처리 로직
    @Transactional
    public Payment approvePayment(UUID paymentIdx, String updatedBy) {
        Payment payment = paymentRepository.findById(paymentIdx)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.approvePaymentSetter(updatedBy);

        return paymentRepository.save(payment); //업데이트 정보 저장
    }

    //결제 취소 처리 로직
    public void cancelPayment(UUID payment_idx, String updated_by) {

        Payment payment = paymentRepository.findById(payment_idx)
                .orElseThrow(() -> new EntityNotFoundException("해당 결제를 찾을 수 없습니다."));

        // 결제 상태를 취소로 변경
        payment.cancelPaymentSetter(updated_by);

        // 상태 저장
        paymentRepository.save(payment);
    }
}
