package com.shopping;

import java.math.BigDecimal;

public class LineItem {
    private final String sku;
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public LineItem(final String sku, final String name, final BigDecimal price, final int quantity) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
