package com.kellysyp.payment_gateway_simulator.service;

import com.kellysyp.payment_gateway_simulator.dto.PaymentRequest;
import com.kellysyp.payment_gateway_simulator.dto.PaymentResponse;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.model.TransactionStatus;
import com.kellysyp.payment_gateway_simulator.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public PaymentService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PaymentResponse authorize(PaymentRequest request) {

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setCardLast4(request.getCardLast4());
        transaction.setCardBrand(request.getCardBrand());
        transaction.setStore(request.getStore());
        transaction.setTerminal(request.getTerminal());

        // Simulated decline rules
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

        // Simulated approval
        String authCode = String.valueOf(100000 + new Random().nextInt(900000));

        transaction.setStatus(TransactionStatus.AUTHORIZED);
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
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != TransactionStatus.AUTHORIZED) {
            return new PaymentResponse(
                    transaction.getTransactionId(),
                    transaction.getStatus().name(),
                    transaction.getAuthCode(),
                    "12",
                    "Invalid transaction state for capture"
            );
        }

        transaction.setStatus(TransactionStatus.CAPTURED);
        transactionRepository.save(transaction);

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
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

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
        transactionRepository.save(transaction);

        return new PaymentResponse(
                transaction.getTransactionId(),
                "VOIDED",
                transaction.getAuthCode(),
                "00",
                "Authorization voided"
        );
    }

}
