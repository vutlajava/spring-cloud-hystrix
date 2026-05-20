package com.javatechie.os.api.dto;

import com.javatechie.os.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Order order;
    private String paymentTransactionId;
    private String paymentTransactionStatus;
    private Integer amount;


}
