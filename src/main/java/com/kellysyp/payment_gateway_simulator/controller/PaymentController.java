package com.kellysyp.payment_gateway_simulator.controller;

import com.kellysyp.payment_gateway_simulator.dto.*;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.service.PaymentService;
import com.kellysyp.payment_gateway_simulator.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<PaymentResponse> authorize(
            @RequestBody PaymentRequest request
    ) {
        PaymentResponse response = paymentService.authorize(request);
        log.info("Authorize request received for amount={}", request.getAmount());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/capture")
    public ResponseEntity<PaymentResponse> capture(
            @RequestBody CaptureRequest request
    ) {
        PaymentResponse response = paymentService.capture(request.getTransactionId());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/void")
    public ResponseEntity<PaymentResponse> voidAuthorization(
            @RequestBody VoidRequest request
    ) {
        PaymentResponse response = paymentService.voidAuthorization(request.getTransactionId());
        return ResponseEntity.ok(response);
    }

   /* @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refund(
            @RequestBody RefundRequest request
    ) {
        PaymentResponse response = paymentService.refund(request.getTransactionId());
        return ResponseEntity.ok(response);
    }*/

    @PostMapping("/transactions/{id}/refund")
    public TransactionResponse refund(
            @PathVariable String id,
            @RequestBody RefundRequest request) {

        Transaction tx = paymentService.refund(id, request.getAmount());
        log.info("Refund request received for transactionId={}, amount={}",
                id, request.getAmount());
        return TransactionResponse.from(tx);
    }

}
