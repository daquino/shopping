package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class OrderRegister {
    private final OrderRepository orderRepository;
    private final float taxPercentage;

    public OrderRegister(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.taxPercentage = 0.0925f;
    }

    public Order placeOrder(final ShoppingCart cart,
                            final String userId,
                            final ShippingAddress shippingAddress) {
        BigDecimal subtotal = cart.getSubtotal();
        BigDecimal tax = calculateTax(subtotal);
        Order order = new Order(generateOrderId(), subtotal, tax, calculateTotal(subtotal, tax),
                userId, shippingAddress, cart.getLineItems());
        orderRepository.save(order);
        return order;
    }

    private BigDecimal calculateTax(final BigDecimal subtotal) {
        return subtotal.multiply(BigDecimal.valueOf(taxPercentage)).setScale(2, RoundingMode.HALF_UP);
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    private BigDecimal calculateTotal(final BigDecimal subtotal, final BigDecimal tax) {
        return subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);
    }
}
