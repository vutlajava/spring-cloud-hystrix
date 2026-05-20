package com.javatechie.os.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.os.api.dto.Payment;
import com.javatechie.os.api.dto.TransactionRequest;
import com.javatechie.os.api.dto.TransactionResponse;
import com.javatechie.os.api.entity.Order;
import com.javatechie.os.api.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RefreshScope
@Slf4j
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Value("${microservice.payment-service.endpoints.paymentid.uri}")
    private String PAYMENT_PAYMENT_ID_ENDPOINT;


    @Value("${microservice.payment-service.endpoints.save.uri}")
    private String PAYMENT_SAVE_END_POINT;

   // @Value( "${microservice.order-service.endpoints.get.uri}")
   // private String ORDER_ID_END_POINT;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {
        Order orderReq = transactionRequest.getOrder();
        System.out.println(orderReq.toString());
        Order orderResponse = orderRepository.save(orderReq);
        Payment payment = new Payment();
        payment.setOrderId(orderResponse.getId());
        payment.setAmount(orderResponse.getPrice());
        logger.info("Order-service request {} ", new ObjectMapper().writeValueAsString(transactionRequest));
        //If you want to pass the order id to payment table we have to create a rest api call
        //transactionRequest.setOrder(orderResponse);
        Payment paymentResponse = restTemplate.postForObject(PAYMENT_SAVE_END_POINT, payment, Payment.class);
        paymentResponse.setPaymentStatus(
                paymentResponse.getPaymentStatus().equals("success") ? "Payment successful" : " Payment failed");
        logger.info("Payment-service response {} ", new ObjectMapper().writeValueAsString(paymentResponse));
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setOrder(orderResponse);
        transactionResponse.setPaymentTransactionId(paymentResponse.getTransactionId());
        transactionResponse.setPaymentTransactionStatus(paymentResponse.getPaymentStatus());
        transactionResponse.setAmount(paymentResponse.getAmount());
        return transactionResponse;
    }

    public TransactionResponse getOrderPaymentDetails(Integer id){

        Order order = orderRepository.findById(id).get();

        TransactionResponse transactionResponse = new TransactionResponse();

        Payment orderResponse= restTemplate.getForObject(PAYMENT_PAYMENT_ID_ENDPOINT+"id", Payment.class);
        transactionResponse.setOrder(order);
        transactionResponse.setPaymentTransactionId(orderResponse.getTransactionId());
        transactionResponse.setPaymentTransactionStatus(orderResponse.getPaymentStatus());
        transactionResponse.setAmount(orderResponse.getAmount());

       return transactionResponse;


    }

}
