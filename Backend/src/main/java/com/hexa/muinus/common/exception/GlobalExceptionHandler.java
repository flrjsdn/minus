package com.hexa.muinus.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * MuinusException 예외 처리
     */
    @ExceptionHandler(MuinusException.class)
    public ResponseEntity<Map<String, Object>> handleMuinusException(MuinusException e) {
        log.error("MuinusException 발생: {} | details: {}", e.getErrorCode().getMessage(), e.getDetails());

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("errorCode", e.getErrorCode().getCode());  // 커스텀 에러 코드 반환
        errorDetails.put("message", e.getErrorCode().getMessage()); // 기본 메시지 반환
        errorDetails.put("details", e.getDetails()); // 추가 정보 포함
        errorDetails.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(e.getErrorCode().getCode() / 100)
                .body(errorDetails);
    }




    /**
     * Validation 예외 처리 (MethodArgumentNotValidException)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation 예외 발생: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", APIErrorCode.INVALID_INPUT.getCode());
        response.put("timestamp", LocalDateTime.now());
        response.put("message", APIErrorCode.INVALID_INPUT.getMessage());

        // 필드별 오류 메시지 추가
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());
        response.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(APIErrorCode.INVALID_INPUT.getCode() / 100)
                .body(response);
    }

    /**
     * 모든 예외 (Exception) 처리 (서버 내부 오류)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalExceptions(Exception ex) {
        log.error("Unhandled 예외 발생: {}", ex.getMessage(), ex);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", APIErrorCode.INTERNAL_SERVER_ERROR.getCode());
        response.put("details", "Unexpected Error");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(APIErrorCode.INTERNAL_SERVER_ERROR.getCode() / 100)
                .body(response);
    }
}
