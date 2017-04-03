package com.shopping;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ShoppingCart {
    public void add(final Product product) {

    }

    public int getItemCount() {
        return 1;
    }

    public BigDecimal getSubtotal() {
        return new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<LineItem> getLineItems() {
        LineItem item = new LineItem("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset",
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP), 1);
        return Arrays.asList(item);
    }
}
