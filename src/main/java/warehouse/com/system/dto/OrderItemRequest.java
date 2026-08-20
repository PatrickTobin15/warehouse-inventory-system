package com.warehouse.system.dto;

// what the client will send for each item on a new order
public class OrderItemRequest {

    private Long productId;
    private int quantity;

    public OrderItemRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

