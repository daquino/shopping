package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private final List<Product> products;
    private final Map<String, Integer> productCounts;

    public ShoppingCart() {
        products = new ArrayList<>();
        productCounts = new HashMap<>();
    }

    public void add(final Product product) {
        int productCount = productCounts.getOrDefault(product.getSku(), 0);
        if (productCount > 0) {
            productCounts.put(product.getSku(), ++productCount);
        }
        else {
            products.add(product);
            productCounts.put(product.getSku(), 1);
        }
    }

    public List<LineEntry> getEntries() {
        List<LineEntry> entries = new ArrayList<>();
        for (Product product : products) {
            int productCount = productCounts.get(product.getSku());
            entries.add(new SimpleLineEntry(product.getSku(), product.getName(), productCount,
                    calculateCost(product.getPrice(), productCount)));
        }
        return entries;
    }

    private BigDecimal calculateCost(final BigDecimal price, final int productCount) {
        return price.multiply(BigDecimal.valueOf(productCount)).setScale(2, RoundingMode.HALF_UP);
    }

    public int getEntryCount() {
        return productCounts.values().stream()
                .mapToInt(price -> price)
                .sum();
    }

    private static class SimpleLineEntry implements LineEntry {
        private final String sku;
        private final String name;
        private final int quantity;
        private final BigDecimal cost;

        private SimpleLineEntry(final String sku, final String name, final int quantity, final BigDecimal cost) {
            this.sku = sku;
            this.name = name;
            this.quantity = quantity;
            this.cost = cost;
        }

        @Override
        public String getSku() {
            return sku;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public BigDecimal getCost() {
            return cost;
        }
    }
}
