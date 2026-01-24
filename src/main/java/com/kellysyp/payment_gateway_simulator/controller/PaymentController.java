package com.kellysyp.payment_gateway_simulator.controller;

import com.kellysyp.payment_gateway_simulator.dto.*;
import com.kellysyp.payment_gateway_simulator.model.Transaction;
import com.kellysyp.payment_gateway_simulator.service.PaymentService;
import com.kellysyp.payment_gateway_simulator.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
        return TransactionResponse.from(tx);
    }

}
