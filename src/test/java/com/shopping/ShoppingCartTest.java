package com.shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    private ShoppingCart cart;

    @Before
    public void setUp() {
        cart = new ShoppingCart();
    }

    @After
    public void tearDown() {
        cart = null;
    }

    @Test
    public void canAddItems() {
        //given
        Product nomProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineItem firstExpectedItem = new LineItem(nomProduct, 1);
        LineItem secondExpectedItem = new LineItem(ponyProduct, 1);

        //when
        cart.add(nomProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(2, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(31.58).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(firstExpectedItem, cart.getLineItems().get(0));
        assertLineEntry(secondExpectedItem, cart.getLineItems().get(1));
    }

    private void assertLineEntry(final LineItem expectedItem, final LineItem actalItem) {
        Assert.assertEquals(expectedItem.getSku(), actalItem.getSku());
        Assert.assertEquals(expectedItem.getName(), actalItem.getName());
        Assert.assertEquals(expectedItem.getQuantity(), actalItem.getQuantity());
        Assert.assertEquals(expectedItem.getPrice(), actalItem.getPrice());
    }

    @Test
    public void canAddSameItemMultipleTimes() {
        //given
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineItem expectedItem = new LineItem(ponyProduct, 3);

        //when
        cart.add(ponyProduct);
        cart.add(ponyProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(3, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(65.97).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(expectedItem, cart.getLineItems().get(0));
    }
}
