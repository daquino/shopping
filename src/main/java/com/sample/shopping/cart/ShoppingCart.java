package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import java.util.*;

public class ShoppingCart {
    private final Map<String, LineEntry> entries;

    public ShoppingCart() {
        entries = new LinkedHashMap<>();
    }

    public void add(final Product product) {
        LineEntry entry = entries.get(product.getSku());
        if(entry != null) {
            CartEntry cartEntry = (CartEntry)entry;
            cartEntry.increment();
        }
        else {
            entries.put(product.getSku(), new CartEntry(product));
        }
    }

    public List<LineEntry> getEntries() {
        return new ArrayList<>(entries.values());
    }

    public int getEntryCount() {
        return entries.values().stream()
                .mapToInt(LineEntry::getQuantity)
                .sum();
    }
}
