package friends.aidelivery.common;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.exception.CommonResultCode;
import friends.aidelivery.common.util.ResponseVOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SampleController {

    public ResponseEntity<CommonResponse> sampleResponse() {
        int responese = 1;
        if (responese == 1) {
            // 성공
            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(responese),
                HttpStatus.OK);
        } else if (responese == 2) {
            // 공통 에러 코드에 정의된 실패
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 에러 코드 커스텀 실패 메시지
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(501, "알 수 없는 이유입니다."),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
