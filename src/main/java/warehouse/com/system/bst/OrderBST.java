package com.warehouse.system.bst;

import java.util.ArrayList;
import java.util.List;

import com.warehouse.system.entity.Order;

// my own binary search tree for order priority.
public class OrderBST {

    private OrderNode root;

    public void insert(Order order) {
        root = insertRecursive(root, order);
    }

    private OrderNode insertRecursive(OrderNode current, Order order) {
        if (current == null) {
            return new OrderNode(order);
        }

        if (order.getPriorityLevel() < current.data.getPriorityLevel()) {
            current.left = insertRecursive(current.left, order);
        } else {
            // priorityLevel values can repeat across orders (>= 1 && <= 10), so that equal priorities are treated the same as "not lower" and sent right. That keeps every priority order that is equal grouped together on the right side of the node it matches, and so that it means inorder traversal still comes out sorted and equal values will just end up right next to each other.
            current.right = insertRecursive(current.right, order);
        }

        return current;
    }

    public List<Order> inorder() {
        List<Order> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(OrderNode node, List<Order> result) {
        if (node == null) {
            return;
        }
        inorderRecursive(node.left, result);
        result.add(node.data);
        inorderRecursive(node.right, result);
    }

    public Order findHighest() {
        if (root == null) {
            throw new IllegalStateException("Priority tree is empty");
        }

        OrderNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    public Order findLowest() {
        if (root == null) {
            throw new IllegalStateException("Priority tree is empty");
        }

        OrderNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
