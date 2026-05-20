package com.javatechie.os.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javatechie.os.api.dto.Payment;
import com.javatechie.os.api.dto.TransactionRequest;
import com.javatechie.os.api.dto.TransactionResponse;
import com.javatechie.os.api.repository.OrderRepository;
import com.javatechie.os.api.service.OrderService;
import com.javatechie.os.api.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

;import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceApplicationTests {

    @Mock
    private OrderRepository orderRepository;

    //@Mock
    //private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getTestSaveOrderPass() throws JsonProcessingException {

        Order order = new Order(1, "ashok", 2, 100);
        Payment mockResponse = new Payment();
        mockResponse.setPaymentStatus("success");
        mockResponse.setId(1);
        mockResponse.setTransactionId("abcabcabc");
        mockResponse.setOrderId(order.getId());
        mockResponse.setAmount(order.getPrice());
        TransactionRequest request = new TransactionRequest(order, mockResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(restTemplate.postForObject(
                nullable(String.class),
                any(Payment.class),
                eq(Payment.class)
        )).thenReturn(mockResponse);
        // Act
        TransactionResponse response = orderService.saveOrder(request);
        // Assert
        assertNotNull(response);
        //assertEquals("ashok", response.getOrder().getName());
        verify(orderRepository, times(1)).save(any(Order.class));

    }

    @Test
    void getTestSaveOrderFail() throws JsonProcessingException {

        Order order = new Order(1, "ashok", 2, 100);
        Payment mockResponse = new Payment();
        mockResponse.setPaymentStatus("fail");
        mockResponse.setId(1);
        mockResponse.setTransactionId("abcabcabc");
        mockResponse.setOrderId(order.getId());
        mockResponse.setAmount(order.getPrice());
        TransactionRequest request = new TransactionRequest(order, mockResponse);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(restTemplate.postForObject(
                nullable(String.class),
                any(Payment.class),
                eq(Payment.class)
        )).thenReturn(mockResponse);
        // Act
        TransactionResponse response = orderService.saveOrder(request);
        // Assert
        assertNotNull(response);
        //assertEquals("ashok", response.getOrder().getName());
        verify(orderRepository, times(1)).save(any(Order.class));

    }
    @Test
    void getTestOrderPaymentDetails(){

        Order order = new Order(1,"ashok",2,10);
        Payment payment = new Payment();
        payment.setId(1);
        payment.setPaymentStatus("success");
        payment.setAmount(order.getPrice());
        payment.setTransactionId("abcabcabc");
        payment.setOrderId(order.getId());

        when(orderRepository.findById(anyInt()))
                .thenReturn(Optional.of(order));
        ResponseEntity<Payment> responseEntity =
                new ResponseEntity<>(payment, HttpStatus.OK);

        when(restTemplate.getForObject(
                anyString(),
                eq(Payment.class)

        )).thenReturn(payment);

        TransactionResponse transactionResponse =
                orderService.getOrderPaymentDetails(payment.getId());

        assertNotNull(transactionResponse);


    }

    @Test
    void testOrderGetterSetter() {
        Order order = new Order();

        order.setId(1);
        order.setName("ashok");
        order.setQty(1);
        order.setPrice(100);

        assertEquals("ashok", order.getName());

    }

    @Test
    void testOrderAllArgsConstructor() {
        Order order = new Order(1,"ashok",1,1);

        assertEquals("ashok", order.getName());
       // assertEquals("john@test.com", dto.getEmail());
    }
    @Test
    void testOrderEqualsAndHashCode() {

        Order order1 = new Order(1,"ashok",2,10);
        Order order2 = new Order(1,"ashok",2,10);
        Order order3 = new Order(1,"ashok",2,10);

        // same values
        assertEquals(order1, order2);
        assertEquals(order1, order1);
        assertEquals(order1.hashCode(), order2.hashCode());

        // different values
      //  assertNotEquals(order1, order2);

        // same reference
        assertEquals(order1, order2);

        // null check
       // assertNotEquals(order1, null);

        // different type (covers instanceof failure)
      //  assertNotEquals(order1, "string");
    }


    @Test
    void contextLoads() {
    }

}
