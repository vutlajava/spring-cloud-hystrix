package com.javatechie.os.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatechie.os.api.dto.Payment;
import com.javatechie.os.api.dto.TransactionRequest;
import com.javatechie.os.api.dto.TransactionResponse;
import com.javatechie.os.api.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
//@CrossOrigin(origins = "http://localhost:9192")
@Slf4j
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

   // @Value( "${microservice.order-service.endpoints.get.uri}")
    //private String ORDER_ID_END_POINT;


    @PostMapping("/saveOrder")
    public ResponseEntity<TransactionResponse> saveOrder(@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {


        TransactionResponse transactionResponse = orderService.saveOrder(transactionRequest);
        logger.info("Saving a order info : {}  ",transactionResponse.getOrder().getId() );
        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getOrderDetails(@PathVariable("id") Integer id) {
        TransactionResponse transactionResponse = orderService.getOrderPaymentDetails(id);
        return new ResponseEntity<TransactionResponse>(transactionResponse,HttpStatus.OK);
    }

}
