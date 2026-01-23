package com.kellysyp.payment_gateway_simulator.dto;

import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.model.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private String transactionId;
    private TransactionStatus status;
    private BigDecimal authorizedAmount;
    private BigDecimal capturedAmount;
    private BigDecimal refundedAmount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TransactionResponse from(Transaction tx) {
        TransactionResponse r = new TransactionResponse();
        r.transactionId = tx.getTransactionId();
        r.status = tx.getStatus();
        r.authorizedAmount = tx.getAuthorizedAmount();
        r.capturedAmount = tx.getCapturedAmount();
        r.refundedAmount = tx.getRefundedAmount();
        r.currency = tx.getCurrency();
        r.createdAt = tx.getCreatedAt();
        r.updatedAt = tx.getUpdatedAt();
        return r;
    }
}
