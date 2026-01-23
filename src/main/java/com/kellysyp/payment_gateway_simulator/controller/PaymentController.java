package com.kellysyp.payment_gateway_simulator.controller;

import com.kellysyp.payment_gateway_simulator.dto.PaymentRequest;
import com.kellysyp.payment_gateway_simulator.dto.PaymentResponse;
import com.kellysyp.payment_gateway_simulator.service.PaymentService;
import com.kellysyp.payment_gateway_simulator.dto.CaptureRequest;
import com.kellysyp.payment_gateway_simulator.dto.VoidRequest;
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

}
