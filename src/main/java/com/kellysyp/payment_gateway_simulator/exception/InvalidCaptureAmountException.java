package com.kellysyp.payment_gateway_simulator.exception;

public class InvalidCaptureAmountException extends RuntimeException {
    public InvalidCaptureAmountException(String message) {
        super(message);
    }
}
