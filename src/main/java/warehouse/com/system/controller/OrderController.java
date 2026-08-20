package com.warehouse.system.controller;

import com.warehouse.system.dto.OrderRequest;
import com.warehouse.system.entity.Order;
import com.warehouse.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // orders are added to the priority tree automatically when created.
    // this is here in case you need to (re)add one manually, e.g. after a restart.
    @PostMapping("/add-to-priority-tree")
    public ResponseEntity<Order> addToPriorityTree(@RequestParam Long orderId) {
        Order order = orderService.addToPriorityTree(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/priority/inorder")
    public ResponseEntity<List<Order>> getInorder() {
        return ResponseEntity.ok(orderService.getInorderPriority());
    }

    @GetMapping("/priority/highest")
    public ResponseEntity<Order> getHighest() {
        return ResponseEntity.ok(orderService.getHighestPriority());
    }

    @GetMapping("/priority/lowest")
    public ResponseEntity<Order> getLowest() {
        return ResponseEntity.ok(orderService.getLowestPriority());
    }
}
