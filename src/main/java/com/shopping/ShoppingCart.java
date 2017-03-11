package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ShoppingCart {
    private final List<Product> products;
    private final Map<String, Integer> productCounts;

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.productCounts = new HashMap<>();
    }

    public void add(final Product product) {
        int count = productCounts.getOrDefault(product.getSku(), 0);
        if (count > 0) {
            productCounts.put(product.getSku(), ++count);
        }
        else {
            products.add(product);
            productCounts.put(product.getSku(), 1);
        }
    }

    public int getItemCount() {
        return productCounts.values().stream()
                .mapToInt(count -> count)
                .sum();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for(Product product: products) {
            int count = productCounts.get(product.getSku());
            subtotal = subtotal.add(calculateItemCost(product.getPrice(), count)).setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal;
    }

    private BigDecimal calculateItemCost(final BigDecimal price, int count) {
        return price.multiply(BigDecimal.valueOf(count)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<LineItem> getLineItems() {
        List<LineItem> items = new ArrayList<>();
        for(Product product: products) {
            int quantity = productCounts.get(product.getSku());
            items.add(new CartLineItem(product, quantity));
        }
        return items;
    }

    private class CartLineItem implements LineItem {
        private final Product product;
        private int quantity;

        public CartLineItem(final Product product, final int quantity) {
            this.product = product;
            this.quantity = quantity;
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
