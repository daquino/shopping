package com.shopping;

import java.util.List;

public class OrderRegister {
    private final OrderRepository orderRepository;

    public OrderRegister(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order submitOrder(final List<LineItem> lineItems, final String user, final ShippingAddress shippingAddress) {
        return null;
    }
}
