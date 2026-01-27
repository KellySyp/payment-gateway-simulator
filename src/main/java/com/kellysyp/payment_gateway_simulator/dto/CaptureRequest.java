package com.kellysyp.payment_gateway_simulator.dto;

import lombok.Getter;

@Getter
public class CaptureRequest {
    private String transactionId;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
