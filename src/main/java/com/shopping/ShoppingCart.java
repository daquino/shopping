package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ShoppingCart {
    private final Map<String, LineItem> items;

    public ShoppingCart() {
        this.items = new LinkedHashMap<>();
    }

    public void add(final Product product) {
        LineItem item = items.get(product.getSku());
        if (item != null) {
            CartLineItem cartLineItem = (CartLineItem) item;
            cartLineItem.increment();
        }
        else {
            items.put(product.getSku(), new CartLineItem(product));
        }
    }

    public int getItemCount() {
        return items.values().stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (LineItem item : items.values()) {
            subtotal = subtotal.add(calculateItemCost(item)).setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal;
    }

    private BigDecimal calculateItemCost(final LineItem item) {
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<LineItem> getLineItems() {
        return new ArrayList<>(items.values());
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

        public void increment() {
            quantity++;
        }
    }
}
