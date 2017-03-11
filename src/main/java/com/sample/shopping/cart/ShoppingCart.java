package com.sample.shopping.cart;

import com.sample.shopping.product.Product;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {
    private final List<LineEntry> entries;

    public ShoppingCart() {
        entries = new ArrayList<>();
    }

    public void add(final Product product) {
        Optional<LineEntry> entry = entries.stream()
                .filter(e -> e.getSku().equals(product.getSku()))
                .findFirst();
        if(entry.isPresent()) {
            CartEntry cartEntry = (CartEntry)entry.get();
            cartEntry.increment();
        }
        else {
            entries.add(new CartEntry(product));
        }
    }

    public List<LineEntry> getEntries() {
        return entries;
    }

    public int getEntryCount() {
        return entries.size();
    }
}
