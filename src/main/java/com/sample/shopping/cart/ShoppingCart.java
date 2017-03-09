package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<CartEntry> entries;

    public ShoppingCart() {
        entries = new ArrayList<CartEntry>();
    }

    public void add(final Product product) {
        entries.add(new CartEntry(product));
    }

    public List<CartEntry> getEntries() {
        return entries;
    }

    public int getEntryCount() {
        return 1;
    }
}
