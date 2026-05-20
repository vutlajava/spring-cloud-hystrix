package com.javatechie.ps.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatechie.ps.api.entity.Payment;
import com.javatechie.ps.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("savePayment")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment paymentReq) throws JsonProcessingException {

        return new ResponseEntity<>(paymentService.savePayment(paymentReq), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(paymentService.getPayment(id),HttpStatus.OK);
    }

}
