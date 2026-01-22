package com.kellysyp.payment_gateway_simulator.repository;

import com.kellysyp.payment_gateway_simulator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}