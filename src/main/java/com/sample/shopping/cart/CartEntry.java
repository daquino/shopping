package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartEntry implements LineEntry {
    private final Product product;
    private int quantity;

    public CartEntry(final Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public String getSku() {
        return product.getSku();
    }

    public String getName() {
        return product.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getCost() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public void increment() {
        quantity++;
    }
}
