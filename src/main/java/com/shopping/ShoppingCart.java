package com.shopping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<LineItem> lineItems;

    public ShoppingCart() {
        this.lineItems = new ArrayList<>();
    }

    public void add(final Product product) {
        lineItems.add(new LineItem(product, 1));
    }

    public int getItemCount() {
        return lineItems.size();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (LineItem item : lineItems) {
            subtotal = subtotal.add(item.getCost());
        }
        return subtotal;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}

