package com.kellysyp.payment_gateway_simulator.service;

import com.kellysyp.payment_gateway_simulator.dto.PaymentRequest;
import com.kellysyp.payment_gateway_simulator.dto.PaymentResponse;
import com.kellysyp.payment_gateway_simulator.exception.*;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.model.TransactionStatus;
import com.kellysyp.payment_gateway_simulator.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public PaymentService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PaymentResponse authorize(PaymentRequest request) {

        Transaction transaction = new Transaction();
        transaction.setCapturedAmount(BigDecimal.ZERO);
        transaction.setRefundedAmount(BigDecimal.ZERO);
        transaction.setAuthorizedAmount(request.getAmount());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setCardLast4(request.getCardLast4());
        transaction.setCardBrand(request.getCardBrand());
        transaction.setStore(request.getStore());
        transaction.setTerminal(request.getTerminal());

        if (request.getAmount().doubleValue() > 500) {
            transaction.setStatus(TransactionStatus.DECLINED);
            transaction.setResponseCode("51");
            transactionRepository.save(transaction);

            return new PaymentResponse(
                    transaction.getTransactionId(),
                    "DECLINED",
                    null,
                    "51",
                    "Insufficient Funds"
            );
        }

        if ("0000".equals(request.getCardLast4())) {
            transaction.setStatus(TransactionStatus.DECLINED);
            transaction.setResponseCode("05");
            transactionRepository.save(transaction);

            return new PaymentResponse(
                    transaction.getTransactionId(),
                    "DECLINED",
                    null,
                    "05",
                    "Do Not Honor"
            );
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentException("Amount must be greater than zero");
        }

        String authCode = String.valueOf(100000 + new Random().nextInt(900000));

        transaction.setStatus(TransactionStatus.AUTHORIZED);
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setAuthCode(authCode);
        transaction.setResponseCode("00");

        transactionRepository.save(transaction);

        return new PaymentResponse(
                transaction.getTransactionId(),
                "AUTHORIZED",
                authCode,
                "00",
                "Approved"
        );
    }

    public PaymentResponse capture(String transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        if (transaction.getStatus() != TransactionStatus.AUTHORIZED) {
            throw new InvalidTransactionStateException(
                    "Only authorized transactions can be captured"
            );
        }

        if (transaction.getAuthorizedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidCaptureAmountException(
                    "Authorized amount must be greater than zero"
            );
        }

        transaction.setCapturedAmount(transaction.getAuthorizedAmount());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.CAPTURED);
        transactionRepository.save(transaction);
        log.info("Transaction captured. transactionId={}, amount={}",
                transaction.getTransactionId(), transaction.getCapturedAmount());

        return new PaymentResponse(
                transaction.getTransactionId(),
                "CAPTURED",
                transaction.getAuthCode(),
                "00",
                "Capture successful"
        );
    }
    public PaymentResponse voidAuthorization(String transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        if (transaction.getStatus() != TransactionStatus.AUTHORIZED) {
            return new PaymentResponse(
                    transaction.getTransactionId(),
                    transaction.getStatus().name(),
                    transaction.getAuthCode(),
                    "12",
                    "Invalid transaction state for void"
            );
        }

        transaction.setStatus(TransactionStatus.VOIDED);
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

        log.info("Transaction voided. transactionId={}, amount={}",
                transaction.getTransactionId(), transaction.getAuthorizedAmount());
        return new PaymentResponse(
                transaction.getTransactionId(),
                "VOIDED",
                transaction.getAuthCode(),
                "00",
                "Authorization voided"
        );
    }

    public Transaction refund(String transactionId, BigDecimal refundAmount) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        if (!(transaction.getStatus() == TransactionStatus.CAPTURED
                || transaction.getStatus() == TransactionStatus.PARTIALLY_REFUNDED)) {
            throw new InvalidTransactionStateException(
                    "Invalid state for refund."
            );
        }

        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRefundAmountException("Amount must be greater than zero");
        }

        BigDecimal remaining =
                transaction.getCapturedAmount().subtract(transaction.getRefundedAmount());

        if (refundAmount.compareTo(remaining) > 0) {
            log.warn("Refund exceeds captured amount. transactionId={}, requested={}, captured={}",
                    transaction.getTransactionId(), refundAmount, transaction.getCapturedAmount());
            throw new InvalidRefundAmountException(
                    "Refund exceeds captured amount"
            );
        }

        BigDecimal newRefunded =
                transaction.getRefundedAmount().add(refundAmount);

        transaction.setRefundedAmount(newRefunded);

        if (newRefunded.compareTo(transaction.getCapturedAmount()) == 0) {
            transaction.setStatus(TransactionStatus.REFUNDED);
        } else {
            transaction.setStatus(TransactionStatus.PARTIALLY_REFUNDED);
        }

        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

}
