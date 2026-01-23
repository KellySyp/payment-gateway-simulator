package com.kellysyp.payment_gateway_simulator.model;

public enum TransactionStatus {
    AUTHORIZED,
    CAPTURED,
    REFUNDED,
    PARTIALLY_REFUNDED,
    VOIDED,
    DECLINED
}