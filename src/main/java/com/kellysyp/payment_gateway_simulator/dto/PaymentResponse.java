package com.kellysyp.payment_gateway_simulator.dto;

public class PaymentResponse {

    private String transactionId;
    private String status;
    private String authCode;
    private String responseCode;
    private String message;

    public PaymentResponse(
            String transactionId,
            String status,
            String authCode,
            String responseCode,
            String message
    ) {
        this.transactionId = transactionId;
        this.status = status;
        this.authCode = authCode;
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
