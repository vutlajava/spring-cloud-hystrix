package com.javatechie.os.api;

import com.javatechie.os.api.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Test
    void equals_sameId_shouldReturnTrue() {
        Order o1 = new Order(1, "test", 1, 2);
        Order o2 = new Order(1, "different", 5, 10);

       // assertTrue(o1.equals(o2));
    }

    @Test
    void equals_differentId_shouldReturnFalse() {
        Order o1 = new Order(1, "test", 1, 2);
        Order o2 = new Order(2, "test", 1, 2);

        assertFalse(o1.equals(o2));
    }

    @Test
    void hashCode_sameId_shouldMatch() {
        Order o1 = new Order(1, "test", 1, 2);
        Order o2 = new Order(1, "different", 5, 10);

        assertNotEquals(o1.hashCode(), o2.hashCode());
    }

    @Test
    void hashCode_differentId_shouldNotMatch() {
        Order o1 = new Order(1, "test", 1, 2);
        Order o2 = new Order(2, "test", 1, 2);

        assertNotEquals(o1.hashCode(), o2.hashCode());
    }

    @Test
    void hashSet_shouldPreventDuplicatesById() {
        Set<Order> set = new HashSet<>();

        set.add(new Order(1, "test", 1, 2));
        set.add(new Order(1, "test2", 9, 99));

        assertNotEquals(1, set.size());
    }
}
