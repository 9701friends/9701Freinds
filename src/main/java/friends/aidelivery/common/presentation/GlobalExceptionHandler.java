package friends.aidelivery.common.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.exception.CustomBadRequestException;
import friends.aidelivery.common.exception.CustomForbiddenException;
import friends.aidelivery.common.exception.CustomNotFoundException;
import friends.aidelivery.common.exception.CustomUnauthorizedException;
import friends.aidelivery.common.exception.code.CommonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomBadRequestException.class})
    public ResponseEntity<CommonResponse> customBadRequestException(
        final CustomBadRequestException ex) {
        log.error("CustomBadRequestException: ", ex);

        return ResponseEntity.badRequest()
            .body(new CommonResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = {CustomNotFoundException.class})
    public ResponseEntity<CommonResponse> customNotFoundException(
        final CustomNotFoundException ex) {
        log.error("CustomNotFoundException: ", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CommonResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = {CustomForbiddenException.class})
    public ResponseEntity<CommonResponse> customForbiddenException(
        final CustomForbiddenException ex) {
        log.error("CustomForbiddenException: ", ex);

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new CommonResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = {CustomUnauthorizedException.class})
    public ResponseEntity<CommonResponse> customUnauthorizedException(
        final CustomUnauthorizedException ex) {
        log.error("CustomUnauthorizedException: ", ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new CommonResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<CommonResponse> runtimeException(final RuntimeException ex) {
        log.error("RuntimeException: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new CommonResponse(CommonResultCode.ERROR.code(),
                CommonResultCode.ERROR.message()));
    }
}