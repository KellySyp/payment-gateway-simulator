package com.kellysyp.payment_gateway_simulator.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CaptureRequest {

    @NotNull(message = "Capture amount is required")
    @DecimalMin(value = "0.01", message = "Capture amount must be greater than zero")
    private String transactionId;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
