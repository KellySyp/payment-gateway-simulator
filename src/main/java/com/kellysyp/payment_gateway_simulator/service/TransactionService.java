package com.kellysyp.payment_gateway_simulator.service;

import com.kellysyp.payment_gateway_simulator.exception.TransactionNotFoundException;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction getTransaction(String transactionId) {
        return repository.findById(transactionId)
                .orElseThrow(() ->
                    new TransactionNotFoundException(transactionId)
                );
    }
}
