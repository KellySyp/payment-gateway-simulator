package com.kellysyp.payment_gateway_simulator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API error response")
public class ApiError {
    @Schema(description = "Error code identifying the type of error", example = "TRANSACTION_NOT_FOUND")
    private String code;

    @Schema(description = "Human-readable error message", example = "Transaction not found")
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
        System.out.println("Message: "+message);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
