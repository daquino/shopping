package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class OrderRegister {
    private final OrderRepository orderRepository;
    private final TaxCalculator taxCalculator;

    public OrderRegister(final OrderRepository orderRepository, final TaxCalculator taxCalculator) {
        this.orderRepository = orderRepository;
        this.taxCalculator = taxCalculator;
    }

    public Order placeOrder(final ShoppingCart cart,
                            final String userId,
                            final ShippingAddress shippingAddress) {
        BigDecimal subtotal = cart.getSubtotal();
        BigDecimal tax = taxCalculator.calculate(subtotal, shippingAddress.getState());
        Order order = new Order(generateOrderId(), subtotal, tax, calculateTotal(subtotal, tax),
                userId, shippingAddress, cart.getLineItems());
        orderRepository.save(order);
        return order;
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    private BigDecimal calculateTotal(final BigDecimal subtotal, final BigDecimal tax) {
        return subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);
    }
}
