package com.kellysyp.payment_gateway_simulator.exception;

import com.kellysyp.payment_gateway_simulator.dto.ApiError;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
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

    @ExceptionHandler(Exception.class)
    @Hidden
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError error = new ApiError("INTERNAL_ERROR", "Unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
