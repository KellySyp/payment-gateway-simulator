package com.kellysyp.payment_gateway_simulator.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CaptureRequest {
    private String transactionId;
    //private BigDecimal amount;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
