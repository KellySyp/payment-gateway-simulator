package com.kellysyp.payment_gateway_simulator.service;

import com.kellysyp.payment_gateway_simulator.dto.PaymentRequest;
import com.kellysyp.payment_gateway_simulator.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    public PaymentResponse authorize(PaymentRequest request) {

        // Simulated decline rules
        if (request.getAmount().doubleValue() > 500) {
            return decline("51", "Insufficient Funds");
        }

        if ("0000".equals(request.getCardLast4())) {
            return decline("05", "Do Not Honor");
        }

        // Simulated approval
        String transactionId = UUID.randomUUID().toString();
        String authCode = String.valueOf(100000 + new Random().nextInt(900000));

        return new PaymentResponse(
                transactionId,
                "AUTHORIZED",
                authCode,
                "00",
                "Approved"
        );
    }

    private PaymentResponse decline(String responseCode, String message) {
        return new PaymentResponse(
                null,
                "DECLINED",
                null,
                responseCode,
                message
        );
    }
}
