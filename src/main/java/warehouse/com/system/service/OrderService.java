package com.warehouse.system.service;

import com.warehouse.system.bst.OrderBST;
import com.warehouse.system.dto.OrderItemRequest;
import com.warehouse.system.dto.OrderRequest;
import com.warehouse.system.entity.Customer;
import com.warehouse.system.entity.Order;
import com.warehouse.system.entity.OrderItem;
import com.warehouse.system.entity.Product;
import com.warehouse.system.exception.InvalidInputException;
import com.warehouse.system.exception.ResourceNotFoundException;
import com.warehouse.system.repository.CustomerRepository;
import com.warehouse.system.repository.OrderRepository;
import com.warehouse.system.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // An in memory BST that mirrors the the orders table, which is keyed by priorityLevel
    private final OrderBST priorityTree = new OrderBST();

    // rebuild the tree from the database on startup so priority lookups still work after doing a restart even though the tree itself isn't 
    @PostConstruct
    public void loadExistingOrdersIntoTree() {
        for (Order order : orderRepository.findAll()) {
            priorityTree.insert(order);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(OrderRequest request) {
        if (request.getCustomerId() == null) {
            throw new InvalidInputException("customerId is required");
        }
        if (request.getPriorityLevel() < 1 || request.getPriorityLevel() > 10) {
            throw new InvalidInputException("priorityLevel must be between 1 and 10");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidInputException("A order needs have at least one item");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No customer with id " + request.getCustomerId()));

        LocalDate orderDate = request.getOrderDate() != null ? request.getOrderDate() : LocalDate.now();

        Order order = new Order(orderDate, request.getPriorityLevel(), customer);
        order = orderRepository.save(order);

        for (OrderItemRequest itemRequest : request.getItems()) {
            if (itemRequest.getQuantity() <= 0) {
                throw new InvalidInputException("Item quantity must be greater than 0");
            }

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No product with id " + itemRequest.getProductId()));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InvalidInputException(
                        "Not enough stock for product '" + product.getName() + "'");
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(itemRequest.getQuantity(), product, order);
            order.getOrderItems().add(orderItem);
        }

        order = orderRepository.save(order);
        priorityTree.insert(order);

        return order;
    }

    // a manual endpoint
    public Order addToPriorityTree(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("No order with id " + orderId));
        priorityTree.insert(order);
        return order;
    }

    public List<Order> getInorderPriority() {
        return priorityTree.inorder();
    }

    public Order getHighestPriority() {
        return priorityTree.findHighest();
    }

    public Order getLowestPriority() {
        return priorityTree.findLowest();
    }
}
