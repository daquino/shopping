package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<LineEntry> entries;

    public ShoppingCart() {
        entries = new ArrayList<LineEntry>();
    }

    public void add(final Product product) {
        entries.add(new CartEntry(product));
    }

    public List<LineEntry> getEntries() {
        return entries;
    }

    public int getEntryCount() {
        return 1;
    }
}
