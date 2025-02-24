package friends.aidelivery.payment.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.payment.application.dto.PaymentRequest;
import friends.aidelivery.payment.application.dto.PaymentResponse;
import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.domain.PaymentStatus;
import friends.aidelivery.payment.domain.repository.PaymentRepository;
import friends.aidelivery.payment.exception.PaymentCheckException;
import friends.aidelivery.payment.exception.PaymentNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentResponse> getPaymentsByUserId(final Long userId, final int page,
        final int size, final String sortBy, final boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Payment> payments = paymentRepository.findByUserId(userId, pageable);
        return payments.getContent().stream().map(PaymentResponse::of).collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByStoreId(final UUID storeId, final int page,
        final int size, final String sortBy, final boolean isAsc) {
        Pageable pageable = createPageable(page, size, sortBy, isAsc);
        Page<Payment> payments = paymentRepository.findByStoreId(storeId, pageable);
        return payments.getContent().stream().map(PaymentResponse::of).collect(Collectors.toList());
    }

    public PaymentResponse getPayment(UUID paymentId) {
        Payment payment = getPaymentOrElseThrow(paymentId);
        return PaymentResponse.of(payment);
    }

    @Transactional(noRollbackFor = PaymentCheckException.class)
    public PaymentResponse createPayment(final PaymentRequest request,
        final UserDetailsImpl userDetails) {
        final Long userId = userDetails.getUserId();
        final UUID orderId = request.orderId();
        final Long price = request.price();
        final UUID storeId = request.storeId();
        final Payment payment = new Payment(userId, orderId, price, PaymentStatus.PENDING, storeId);
        final Payment saved = paymentRepository.save(payment);

        try {
            requestPayment(saved.getId());
        } catch (Exception e) {
            saved.updatePaymentState(PaymentStatus.REJECTED);
            throw new PaymentCheckException(saved.getId());
        }
        saved.updatePaymentState(PaymentStatus.COMPLETED);
        return PaymentResponse.of(saved);
    }

    @Transactional(noRollbackFor = PaymentCheckException.class)
    public PaymentResponse cancelPayment(UUID orderId) {

        final Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new PaymentNotFoundException(orderId));

        try {
            requestPayment(payment.getId());
        } catch (Exception e) {
            payment.updatePaymentState(PaymentStatus.REJECTED);
            throw new PaymentCheckException(payment.getId());
        }

        payment.updatePaymentState(PaymentStatus.CANCELED);

        return PaymentResponse.of(payment);
    }

    // 외부 api 요청
    private void requestPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(RuntimeException::new);
    }

    public Payment getPaymentOrElseThrow(UUID paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    private Pageable createPageable(int page, int size, String sortBy, boolean isAsc) {
        final Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }
}
