package com.warehouse.system.bst;

import com.warehouse.system.entity.Order;

public class OrderNode {

    Order data;
    OrderNode left;
    OrderNode right;

    public OrderNode(Order data) {
        this.data = data;
    }
}
