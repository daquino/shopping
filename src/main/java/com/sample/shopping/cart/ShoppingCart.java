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
    private final Map<String, Integer> productCountMap;

    public ShoppingCart() {
        products = new ArrayList<>();
        productCountMap = new HashMap<>();
    }

    public void add(final Product product) {
        int productCount = productCountMap.getOrDefault(product.getSku(), 0);
        if (productCount > 0) {
            productCountMap.put(product.getSku(), ++productCount);
        }
        else {
            products.add(product);
            productCountMap.put(product.getSku(), 1);
        }
    }

    public List<LineEntry> getEntries() {
        List<LineEntry> entries = new ArrayList<>();
        for (Product product : products) {
            int productCount = productCountMap.get(product.getSku());
            entries.add(new SimpleLineEntry(product.getSku(), product.getName(), productCount,
                    calculateCost(product.getPrice(), productCount)));
        }
        return entries;
    }

    private BigDecimal calculateCost(final BigDecimal price, final int productCount) {
        return price.multiply(BigDecimal.valueOf(productCount)).setScale(2, RoundingMode.HALF_UP);
    }

    public int getEntryCount() {
        return productCountMap.values().stream()
                .mapToInt(price -> price)
                .sum();
    }

    private static class SimpleLineEntry implements LineEntry {
        private final String sku;
        private final String name;
        private final int quantity;
        private final BigDecimal cost;

        public SimpleLineEntry(final String sku, final String name, final int quantity, final BigDecimal cost) {
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
