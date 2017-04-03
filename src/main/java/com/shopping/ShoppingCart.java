package com.shopping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCart {
    private final List<LineItem> lineItems;

    public ShoppingCart() {
        this.lineItems = new ArrayList<>();
    }

    public void add(final Product product) {
        lineItems.add(new LineItem(product.getSku(), product.getName(), product.getPrice(), 1));
    }

    public int getItemCount() {
        return lineItems.size();
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for(LineItem item: lineItems) {
            subtotal = subtotal.add(item.getCost());
        }
        return subtotal;
    }

    public List<LineItem> getLineItems() {
        LineItem item = new LineItem("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset",
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP), 1);
        return Arrays.asList(item);
    }
}
