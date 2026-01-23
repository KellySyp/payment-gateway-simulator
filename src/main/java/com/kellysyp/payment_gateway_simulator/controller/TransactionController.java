package com.kellysyp.payment_gateway_simulator.controller;

import com.kellysyp.payment_gateway_simulator.dto.TransactionResponse;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

  /*  @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable String id) {
        return service.getTransaction(id);
    }*/
    @GetMapping("/{id}")
    public TransactionResponse getTransaction(@PathVariable String id) {
        return TransactionResponse.from(service.getTransaction(id));
    }
}