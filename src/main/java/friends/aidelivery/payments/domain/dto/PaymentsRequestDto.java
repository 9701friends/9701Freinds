package friends.aidelivery.payments.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaymentsRequestDto {
    //Payments 값 바꿈 -> db 연동 -> db값 바뀔 수 있음.
    //controller에서 db연결하면 안됨. -> dto를 사용해서!
    //값을 가져오거나 insert를 할때!

    private String userId;
    @JsonProperty("orderId")
    private String orderId;
    private Double paymentAmount;
    private String createdBy;
}
