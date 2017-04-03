package com.shopping;

import java.math.BigDecimal;

public class LineItem {
    private final Product product;
    private final int quantity;

    public LineItem(final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getSku() {
        return product.getSku();
    }

    public String getName() {
        return product.getName();
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getCost() {
        return product.getPrice().multiply(new BigDecimal(quantity)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
