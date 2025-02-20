package friends.aidelivery.payment.application;

import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.domain.repository.PaymentRepository;
import friends.aidelivery.payment.exception.PaymentNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    //결제 요청 처리 로직
    @Transactional
    //user_id는 String 으로, order_id는 유효아이디로
    //dto 활용해서 하기
    public Payment requestPayment(long user_id, UUID order_id, Double payment) {
        Payment p = new Payment(user_id, order_id, payment, "REQUESTED");

        // 결제 요청 저장
        return paymentRepository.save(p);
    }

    //결제 승인 처리 로직
    @Transactional
    public void approvePayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.updatePaymentState("APPROVED");

        paymentRepository.save(payment); //업데이트 정보 저장
    }

    //결제 취소 처리 로직
    @Transactional
    public void cancelPayment(UUID paymentId) {

        //결제 찾기
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        // 결제 상태를 취소로 변경
        payment.updatePaymentState("CANCELED");

        // 상태 저장
        paymentRepository.save(payment);
    }


    // userId로 결제 내역 전체 조회
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByUserId(Long userId) {
        paymentRepository.findByUserId(userId);
        return paymentRepository.findAll();
    }


    //userId와 paymentId에 해당하는 결제 조회
    @Transactional(readOnly = true)
    public Payment getPaymentByPaymentIdAndUserId(UUID paymentId, Long userId) {
        return paymentRepository.findByPaymentIdAndUserId(paymentId, userId)
                .orElse(null); // 없으면 null 반환
    }
}
