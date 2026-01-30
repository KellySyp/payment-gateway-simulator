package com.kellysyp.payment_gateway_simulator.dto;

import java.math.BigDecimal;

public class RefundRequest {

    private String transactionId;
    private BigDecimal amount;

    /*public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }*/

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
