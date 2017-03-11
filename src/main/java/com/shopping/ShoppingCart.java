package com.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        for (Product product : products) {
            int count = productCounts.get(product.getSku());
            subtotal = subtotal.add(calculateItemCost(product.getPrice(), count)).setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal;
    }

    private BigDecimal calculateItemCost(final BigDecimal price, int count) {
        return price.multiply(BigDecimal.valueOf(count)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<LineItem> getLineItems() {
        return products.stream()
                .map(product -> new LineItem(product, productCounts.get(product.getSku())))
                .collect(Collectors.toList());
    }
}
