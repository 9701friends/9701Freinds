package friends.aidelivery.payment.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PaymentRequestDto {
    //Payments 값 바꿈 -> db 연동 -> db값 바뀔 수 있음.
    //controller에서 db연결하면 안됨. -> dto를 사용해서!
    //값을 가져오거나 insert를 할때!
    private String user_id;
    private String order_id;
    private Double payment;
    private String created_by;
    private String updated_by;
}
