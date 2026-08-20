package com.warehouse.system.bst;

import com.warehouse.system.entity.Customer;
import com.warehouse.system.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBSTTest {

    private OrderBST tree;
    private Customer customer;

    @BeforeEach
    void setUp() {
        tree = new OrderBST();
        customer = new Customer("Test Customer", "test@example.com");
    }

    private Order order(int priority) {
        return new Order(LocalDate.now(), priority, customer);
    }

    @Test
    void inorderTraversalReturnsOrdersSortedByPriority() {
        tree.insert(order(5));
        tree.insert(order(2));
        tree.insert(order(8));
        tree.insert(order(1));
        tree.insert(order(9));

        List<Order> result = tree.inorder();
        List<Integer> priorities = result.stream().map(Order::getPriorityLevel).toList();

        assertEquals(List.of(1, 2, 5, 8, 9), priorities);
    }

    @Test
    void findHighestAndFindLowestReturnCorrectOrders() {
        tree.insert(order(5));
        tree.insert(order(2));
        tree.insert(order(8));
        tree.insert(order(1));
        tree.insert(order(9));

        assertEquals(9, tree.findHighest().getPriorityLevel());
        assertEquals(1, tree.findLowest().getPriorityLevel());
    }

    @Test
    void duplicatePrioritiesAreKeptAndStayInSortedOrder() {
        tree.insert(order(5));
        tree.insert(order(5));
        tree.insert(order(3));
        tree.insert(order(7));

        List<Order> result = tree.inorder();
        List<Integer> priorities = result.stream().map(Order::getPriorityLevel).toList();

        // both 5s should show up, and the list should still be sorted
        assertEquals(List.of(3, 5, 5, 7), priorities);
    }

    @Test
    void findHighestOnEmptyTreeThrows() {
        assertThrows(IllegalStateException.class, () -> tree.findHighest());
    }

    @Test
    void findLowestOnEmptyTreeThrows() {
        assertThrows(IllegalStateException.class, () -> tree.findLowest());
    }
}
