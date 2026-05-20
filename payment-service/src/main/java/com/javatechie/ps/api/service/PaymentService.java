package com.javatechie.ps.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.ps.api.entity.Payment;
import com.javatechie.ps.api.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) throws JsonProcessingException {

        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(paymentProcessing());
        logger.info("Payment-service request {} ",new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);
    }

    public String paymentProcessing() {

        //Implement actual API calls
        return new Random().nextBoolean() ? "success":"failed";
    }

    public Payment getPayment(Integer id) {
        return paymentRepository.findByOrderId(id);

    }
}
