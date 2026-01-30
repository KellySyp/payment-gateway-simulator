package com.kellysyp.payment_gateway_simulator.exception;

import com.kellysyp.payment_gateway_simulator.dto.ApiError;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiError> handleTransactionNotFound(
            TransactionNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "TRANSACTION_NOT_FOUND",
                        ex.getMessage()
                ));
    }


    @ExceptionHandler(InvalidRefundAmountException.class)
    public ResponseEntity<ApiError> handleInvalidRefund(
            InvalidRefundAmountException ex) {

        return ResponseEntity.badRequest()
                .body(new ApiError(
                        "INVALID_REFUND",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidCaptureAmountException.class)
    public ResponseEntity<ApiError> handleInvalidCapture(
            InvalidCaptureAmountException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError("INVALID_CAPTURE", ex.getMessage()));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ApiError> handleInvalidPayment(
            InvalidPaymentException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError("INVALID_PAYMENT", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(InvalidTransactionStateException.class)
    public ResponseEntity<ApiError> handleInvalidState(
            InvalidTransactionStateException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ApiError("INVALID_STATE", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @Hidden
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError error = new ApiError("INTERNAL_ERROR", "Unexpected error occurred");
        log.error("Unhandled exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
