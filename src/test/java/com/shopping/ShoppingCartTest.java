package com.shopping;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    @Test
    public void canAddASingleItem() {
        //given
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        LineItem expectedLineItem = new LineItem("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset",
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP), 1);

        //when
        cart.add(product);

        //then
        Assert.assertEquals(1, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(expectedLineItem, cart.getLineItems().get(0));

    }

    private void assertLineEntry(final LineItem expectedItem, final LineItem actalItem) {
        Assert.assertEquals(expectedItem.getSku(), actalItem.getSku());
        Assert.assertEquals(expectedItem.getName(), actalItem.getName());
        Assert.assertEquals(expectedItem.getQuantity(), actalItem.getQuantity());
        Assert.assertEquals(expectedItem.getCost(), actalItem.getCost());
    }
}
