package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<LineItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void add(final Product product) {
        items.add(new CartLineItem(product));
    }

    public int getItemCount() {
        return items.size();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (LineItem item : items) {
            subtotal = subtotal.add(calculateItemCost(item)).setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal;
    }

    private BigDecimal calculateItemCost(final LineItem item) {
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<LineItem> getLineItems() {
        return items;
    }

    private class CartLineItem implements LineItem {
        private final Product product;
        private int quantity;

        public CartLineItem(final Product product) {
            this.product = product;
            quantity = 1;
        }

        @Override
        public String getSku() {
            return product.getSku();
        }

        @Override
        public String getName() {
            return product.getName();
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public BigDecimal getPrice() {
            return product.getPrice();
        }
    }
}