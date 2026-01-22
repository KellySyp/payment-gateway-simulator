package com.kellysyp.payment_gateway_simulator.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private BigDecimal amount;
    private String currency;
    private String cardLast4;
    private String cardBrand;
    private String terminal;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardLast4() {
        return cardLast4;
    }

    public void setCardLast4(String cardLast4) {
        this.cardLast4 = cardLast4;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}