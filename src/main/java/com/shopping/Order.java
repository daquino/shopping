package com.shopping;

import java.math.BigDecimal;

public class Order {
    private String orderId;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private ShippingAddress shippingAddress;

    public Order(final String orderId, final BigDecimal subtotal, final BigDecimal tax, final BigDecimal total,
                 final ShippingAddress shippingAddress) {
        this.orderId = orderId;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getShippingAddressTo() {
        return shippingAddress.getTo();
    }

    public String getShippingAddressLineOne() {
        return shippingAddress.getLineOne();
    }

    public String getShippingAddressLineTwo() {
        return shippingAddress.getLineTwo();
    }


    public String getShippingAddressCity() {
        return shippingAddress.getCity();
    }

    public String getShippingAddressState() {
        return shippingAddress.getState();
    }

    public String getShippingAddressZip() {
        return shippingAddress.getZip();
    }
}
