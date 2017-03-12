package com.shopping;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private String orderId;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private String userId;
    private ShippingAddress shippingAddress;
    private List<LineItem> items;

    public Order(final String orderId, final BigDecimal subtotal, final BigDecimal tax, final BigDecimal total,
                 final String userId, final ShippingAddress shippingAddress, final List<LineItem> items) {
        this.orderId = orderId;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.items = items;
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

    public String getUserId() {
        return userId;
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

    public List<LineItem> getItems() {
        return items;
    }
}
