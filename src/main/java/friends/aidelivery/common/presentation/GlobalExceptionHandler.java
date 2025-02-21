package friends.aidelivery.common.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.exception.CustomBadRequestException;
import friends.aidelivery.common.exception.CustomForbiddenException;
import friends.aidelivery.common.exception.CustomNotFoundException;
import friends.aidelivery.common.exception.CustomUnauthorizedException;
import friends.aidelivery.common.exception.code.CommonResultCode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<CommonResponse> handleAccessDeniedException(
        final AccessDeniedException e) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new CommonResponse(CommonResultCode.FORBIDDEN.code(),
                CommonResultCode.FORBIDDEN.message()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(
        final MethodArgumentNotValidException e) {

        List<String> errorMessages = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage) // 오류 메시지만 추출
            .toList();

        String message = errorMessages.isEmpty() ? "입력값 검증 실패" : errorMessages.get(0);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new CommonResponse(CommonResultCode.BAD_REQUEST.code(), message));
    }
}