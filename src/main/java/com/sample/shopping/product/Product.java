package com.sample.shopping.product;

import java.math.BigDecimal;

public class Product {
    private final String sku;
    private final String name;
    private final BigDecimal price;

    public Product(final String sku, final String name, final BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
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
}
